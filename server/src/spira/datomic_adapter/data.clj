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
            [spira.dm.plant-desc :as p]
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

(defn populate-gardens [r]
  (.add-garden r (g/create-garden "Torture"))
  (.add-garden r (g/create-garden "Babylon"))
  )

(defn populate-plant-descriptions [r]
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Morotssläktet" "Morot" "Autumn King"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Morotssläktet" "Morot" "Amsterdam"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Morotssläktet" "Morot" "London Torg"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Morotssläktet" "Morot" "Early Nantes"))

  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Palsternackssläktet" "Palsternacka" "Student"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Palsternackssläktet" "Palsternacka" "White Gem"))

  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Persiljesläktet" "Rotpersilja" "Berliner Halblang"))

  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Selleri" "Stjälkselleri" "Tall Utah"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Selleri" "Bladselleri" "Par-Cel"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Selleri" "Bladselleri" "Kintsai"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Selleri" "Bladselleri" "Heung Kunn"))

  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Dillsläktet" "Dill" "Mammuth"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Dillsläktet" "Dill" "Vanlig Dill"))
  (.add-plant-desc r (p/create-plant-desc "Flockblommiga" "Dillsläktet" "Dill" "Tetra Dill"))

  (.add-plant-desc r (p/create-plant-desc "Gräsväxter" "Majssläktet" "Majs" "Double Standard"))
  (.add-plant-desc r (p/create-plant-desc "Gräsväxter" "Majssläktet" "Majs" "Ashworth"))
  (.add-plant-desc r (p/create-plant-desc "Gräsväxter" "Majssläktet" "Majs" "Painted Mountain"))

  (.add-plant-desc r (p/create-plant-desc "Gurkväxter" "Gurkor" "Gurka" "Northern Pickling"))
  (.add-plant-desc r (p/create-plant-desc "Gurkväxter" "Gurkor" "Gurka" "Arboga Vit"))

  (.add-plant-desc r (p/create-plant-desc "Korgblommiga" "Kronärtskockssläktet" "Kronärtskocka" "Green Globe"))
  (.add-plant-desc r (p/create-plant-desc "Korgblommiga" "Kronärtskockssläktet" "Kronärtskocka" "Imperial Star"))
  )
