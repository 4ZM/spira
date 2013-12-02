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
            [spira.dm.plant-desc :as plant-desc]
            [spira.dm.seeding :as seeding]
            [spira.core.util :as util]
            [datomic.api :as d]))

(def test-uri "datomic:mem://spira")

(defn create-test-db [uri]
  (d/delete-database uri)
  (d/create-database uri)
  (let [conn (d/connect uri)
        schema-tx (read-string (slurp "src/spira/datomic_adapter/schema.edn"))
        data-tx (read-string (slurp "src/spira/datomic_adapter/data.edn"))]
    @(d/transact conn schema-tx)
    @(d/transact conn data-tx))
  nil)

(defn- contains [conn id]
  (not (empty? (d/q '[:find ?id :in $ ?id :where [?id]] (d/db conn) id))))

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
    (if (contains conn id)
      (do
        @(d/transact conn [[:db.fn/retractEntity id]])
        true)
      false))

  (update-garden [_ id g]
    (if (contains conn id)
      (do
        @(d/transact conn [{:db/id id :garden/name (:name g)}])
        true)
      false)))

(defn datomic-garden-repo [uri]
  (->DatomicGardenRepo (d/connect uri)))


;; Helpers to create family/genus/species datoms
(defn- get-named-datom [conn datom name]
  (let [query '[:find ?x :in $ ?datom ?name
                :where [?x ?datom ?name]]]
    (ffirst (d/q query (d/db conn) datom name))))

(defn- create-named-datom [conn datom name]
  (let [tmp-id #db/id[:db.part/user -1337]
        new-datom {:db/id tmp-id datom name}
        tx @(d/transact conn [new-datom])]
    (d/resolve-tempid (d/db conn) (:tempids tx) tmp-id)))

(defn- get-or-create-named-datom [conn datom name]
  (or (get-named-datom conn datom name)
      (create-named-datom conn datom name)))

(defn- get-or-create-family [conn name]
  (get-or-create-named-datom conn :plant.taxonomy.family/name name))
(defn- get-or-create-genus [conn name]
  (get-or-create-named-datom conn :plant.taxonomy.genus/name name))
(defn- get-or-create-species [conn name]
  (get-or-create-named-datom conn :plant.taxonomy.species/name name))



(defrecord DatomicPlantDescriptionRepo [conn]
  plant-desc/PlantDescriptionRepo

  (list-descriptions [_]
    (let [plant-descriptions (d/q '[:find ?pd ?k ?sn :where
                                    [?pd :plant.desc/name ?n]
                                    [?n :plant.name/kind ?k]
                                    [?n :plant.name/species ?s]
                                    [?s :plant.taxonomy.species/name ?sn]]
                                  (d/db conn))]
      (map #(zipmap [:id :kind :species] %) plant-descriptions))
    )

  (get-plant-desc [_ id]
    (let [desc (-> conn d/db (d/entity id))
          name (:plant.desc/name desc)
          family (:plant.taxonomy.family/name (:plant.name/family name))
          genus (:plant.taxonomy.genus/name (:plant.name/genus name))
          species (:plant.taxonomy.species/name (:plant.name/species name))
          kind (:plant.name/kind name)
          ]
      (plant-desc/create-plant-desc family genus species kind)))

  (add-plant-desc [_ desc]

    (let [
          ;; Get the taxonomy names from db - if exists
          family-id (get-or-create-family conn (-> desc :name :family))
          genus-id (get-or-create-genus conn (-> desc :name :genus))
          species-id (get-or-create-species conn (-> desc :name :species))

          ;; Create transaction for adding description
          tmp-name-id #db/id[:db.part/user -1337]
          tmp-desc-id #db/id[:db.part/user -4711]
          new-desc [ {:db/id tmp-name-id
                      :plant.name/family family-id
                      :plant.name/genus genus-id
                      :plant.name/species species-id
                      :plant.name/kind (-> desc :name :kind)}
                     {:db/id tmp-desc-id
                      :plant.desc/name tmp-name-id}]

          ;; Do it
          tx @(d/transact conn new-desc)]

      ;; Return new descriptions id
      (d/resolve-tempid (d/db conn) (:tempids tx) tmp-desc-id)
      ))

  (update-plant-desc [_ id desc]
    (let [
          ;; Get the taxonomy names from db - if exists
          family-id (get-or-create-family conn (-> desc :name :family))
          genus-id (get-or-create-genus conn (-> desc :name :genus))
          species-id (get-or-create-species conn (-> desc :name :species))

          ;; Create transaction for adding description
          tmp-name-id #db/id[:db.part/user -1337]
          new-desc [ {:db/id tmp-name-id
                      :plant.name/family family-id
                      :plant.name/genus genus-id
                      :plant.name/species species-id
                      :plant.name/kind (-> desc :name :kind)}
                     {:db/id id
                      :plant.desc/name tmp-name-id}]]
      ;; Do it
      (if (contains conn id)
        (do
          @(d/transact conn new-desc)
          true)
        false)))
  (delete-plant-desc [_ id]
    ;; Remove plant.name and family, genus, species without plant?
    (if (contains conn id)
      (do
        @(d/transact conn [[:db.fn/retractEntity id]])
        true)
      false)))

(defn datomic-plant-description-repo [uri]
  (->DatomicPlantDescriptionRepo (d/connect uri)))

