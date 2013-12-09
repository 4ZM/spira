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

(ns spira.datomic-adapter.data
  (:require [spira.dm.garden :as g]
            [spira.dm.plant :as p]
            [spira.dm.seeding :as seeding]
            [spira.core.util :as util]
            [datomic.api :as d]))

(defn create-test-db [uri]
  (d/delete-database uri)
  (d/create-database uri)
  (let [conn (d/connect uri)
        schema-tx (read-string (slurp "src/spira/datomic_adapter/schema.edn"))]
    @(d/transact conn schema-tx))
  nil)

(defn populate-gardens! [r]
  (.add-garden r (g/create-garden "Torture"))
  (.add-garden r (g/create-garden "Babylon"))
  )

(defn populate-plant-repo! [r]
  (let [s (p/add-species r (p/create-species
                            "Morot"
                            "Flockblommiga"
                            "Morotssläktet"
                            "Description of morot"))]
    (p/add-kind r (p/create-kind "Autumn King" (:name s) "Desc of autumn king"))
    (p/add-kind r (p/create-kind "Amsterdam" (:name s) "Description"))
    (p/add-kind r (p/create-kind "London Torg" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Early Nantes" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Palsternacka"
                            "Flockblommiga"
                            "Palsternackssläktet"
                            "Description of P"))]
    (p/add-kind r (p/create-kind "Student" (:name s) "Description"))
    (p/add-kind r (p/create-kind "White Gem" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Rotpersilja"
                            "Flockblommiga"
                            "Persiljesläktet"
                            "Description"))]
    (p/add-kind r (p/create-kind "Berliner Halblang" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Stjälkselleri"
                            "Flockblommiga"
                            "Selleri"
                            "Description"))]
    (p/add-kind r (p/create-kind "Tall Utah" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Bladselleri"
                            "Flockblommiga"
                            "Selleri"
                            "Description"))]
    (p/add-kind r (p/create-kind "Par-Cel" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Kintsai" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Heung Kunn" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Dill"
                            "Flockblommiga"
                            "Dillsläktet"
                            "Description"))]
    (p/add-kind r (p/create-kind "Vanlig Dill" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Tetra Dill" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Majs"
                            "Gräsväxter"
                            "Majssläktet"
                            "Description"))]
    (p/add-kind r (p/create-kind "Double Standard" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Ashworth" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Painted Mountain" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Gurka"
                            "Gurkväxter"
                            "Gurkor"
                            "Description"))]
    (p/add-kind r (p/create-kind "Arboga Vit" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Northern Pickling" (:name s) "Description")))

  (let [s (p/add-species r (p/create-species
                            "Kronärtskocka"
                            "Korgblommiga"
                            "Kronärtskockssläktet"
                            "Description"))]
    (p/add-kind r (p/create-kind "Green Globe" (:name s) "Description"))
    (p/add-kind r (p/create-kind "Imperial Star" (:name s) "Description")))

  ;; Return nil - all side effects
  nil
  )

