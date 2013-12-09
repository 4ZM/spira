;; Copyright (C) 2013 Anders Sundman <anders@4zm.org>
;;
;; This program is free software: you can redistribute it and/or modify
;; it under the terms of the GNU Affero General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
;; GNU Affero General Public License for more details.
;;
;; You should have received a copy of the GNU Affero General Public License
;; along with this program. If not, see <http://www.gnu.org/licenses/>.

(ns spira.datomic-adapter.repo
  (:require [spira.dm.garden :as garden]
            [spira.dm.plant :as plant]
            [spira.dm.seeding :as seeding]
            [spira.core.util :as util]
            [datomic.api :as d]))

(defn- contains-id [conn id]
  "TODO remove after refactoring garden repo"
  (seq (d/q '[:find ?id :in $ ?id :where [?id]] (d/db conn) id)))

(defn- delete [conn id]
  "Delete an entity from a db. Return true if an entity was removed.
   Do nothing and return false if the entity is not present."
  (if (contains-id conn id)
    (do
      @(d/transact conn [[:db.fn/retractEntity id]])
      true)
    false))


(defrecord DatomicGardenRepo [conn]
  garden/GardenRepo
  (list-gardens [_]
    (let [gardens (d/q '[:find ?g ?n :where [?g :garden/name ?n]] (d/db conn))]
      (map #(zipmap [:id :name] %) gardens)))

  (get-garden [_ id]
    (let [garden (-> conn d/db (d/entity id))]
      (garden/create-garden (:garden/name garden))))

  (add-garden [_ g]
    (let [tmp-id #db/id[:db.part/user -1337]
          new-garden {:db/id tmp-id :garden/name (:name g)}
          tx @(d/transact conn [new-garden])]
      (d/resolve-tempid (d/db conn) (:tempids tx) tmp-id)))

  (delete-garden [_ id]
    (if (contains-id conn id)
      (do
        @(d/transact conn [[:db.fn/retractEntity id]])
        true)
      false))

  (update-garden [_ id g]
    (if (contains-id conn id)
      (do
        @(d/transact conn [{:db/id id :garden/name (:name g)}])
        true)
      false)))

(defn create-garden-repo [uri]
  (->DatomicGardenRepo (d/connect uri)))

;; Helpers to create family/genus datoms
(defn- get-named-datom [conn datom name]
  (let [query '[:find ?x :in $ ?datom ?name
                :where [?x ?datom ?name]]]
    (ffirst (d/q query (d/db conn) datom name))))

(defn- create-named-datom [conn datom name]
  (let [tmp-id #db/id[:db.part/user -1]
        new-datom {:db/id tmp-id datom name}
        tx @(d/transact conn [new-datom])]
    (d/resolve-tempid (d/db conn) (:tempids tx) tmp-id)))

(defn- get-or-create-named-datom [conn datom name]
  (or (get-named-datom conn datom name)
      (create-named-datom conn datom name)))

(defn- get-or-create-family [conn name]
  (get-or-create-named-datom conn :taxonomy.family/name name))
(defn- get-or-create-genus [conn name]
  (get-or-create-named-datom conn :taxonomy.genus/name name))

(defn- find-species [conn sname]
  "Lookup specis id from name. nil if it is not found."
  (ffirst
   (d/q '[:find ?id :in $ ?sn :where [?id :species/name ?sn]]
        (d/db conn) sname)))

(defrecord DatomicPlantRepo [conn]
  plant/PlantRepo

  (species [_]
    (let [species (d/q '[:find ?s ?sn ?fn ?gn ?d :where
                         [?s :species/name ?sn]
                         [?s :species/family ?f]
                         [?f :taxonomy.family/name ?fn]
                         [?s :species/genus ?g]
                         [?g :taxonomy.genus/name ?gn]
                         [?s :species/description ?d]]
                       (d/db conn))]
      (seq (map (partial apply plant/create-species) species))))

  (species [_ id]
    (let [species (d/q '[:find ?s ?sn ?fn ?gn ?d
                         :in $ ?s :where
                         [?s :species/name ?sn]
                         [?s :species/family ?f]
                         [?f :taxonomy.family/name ?fn]
                         [?s :species/genus ?g]
                         [?g :taxonomy.genus/name ?gn]
                         [?s :species/description ?d]]
                       (d/db conn) id)]
      (if (empty? species)
        nil
        (apply plant/create-species (first species)))))

  (add-species [_ species]
    (let [family-id (get-or-create-family conn (-> species :family))
          genus-id (get-or-create-genus conn (-> species :genus))

          ;; Create a new temp id (add) or get the current id (update).
          id (or (:id species) #db/id[:db.part/user -1])

          ;; Create transaction for adding or updating species.
          new-species [{:db/id id
                        :species/name (-> species :name)
                        :species/family family-id
                        :species/genus genus-id
                        :species/description (-> species :description)}]

          ;; Perform transaction.
          tx @(d/transact conn new-species)]

      ;; Return the species (with valid id).
      (if (:id species)
        species
        (assoc species :id (d/resolve-tempid (d/db conn) (:tempids tx) id)))))

  (delete-species [_ id] (delete conn id))

  (kinds [_ sid]
    (let [kinds (d/q '[:find ?k ?kn ?sn ?kd :in $ ?sid :where
                       [?k :kind/name ?kn]
                       [?k :kind/species ?sid]
                       [?k :kind/description ?kd]
                       [?sid :species/name ?sn]] (d/db conn) sid)]
      (seq (map (partial apply plant/create-kind) kinds))))

  (add-kind [_ kind]
    (let [species-id (find-species conn (:species kind))

          ;; Create a new temp id (add) or get the current id (update).
          id (or (:id kind) #db/id[:db.part/user -1])

          ;; Create transaction for adding or updating kind.
          new-kind [{:db/id id
                     :kind/name (-> kind :name)
                     :kind/species species-id
                     :kind/description (-> kind :description)}]

          ;; Perform transaction.
          tx @(d/transact conn new-kind)]

      ;; Return the kind (with valid id).
      (if (:id kind)
        kind
        (assoc kind :id (d/resolve-tempid (d/db conn) (:tempids tx) id)))))

  (delete-kind [_ id] (delete conn id)))

(defn create-plant-repo [uri]
  (->DatomicPlantRepo (d/connect uri)))
