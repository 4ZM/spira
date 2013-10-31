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

(ns spira.dm.test-model
  (:require [spira.dm.garden :as garden]
            [spira.dm.plant-desc :as plant-desc]
            [spira.dm.seeding :as seeding]
            [spira.core.util :as util]))

;; This class creates and populates a small domain model that can be
;; used in unit tests.

;; Repo
(def id-cnt (ref 0))
(def gardens (ref {}))
(def plant-descriptions (ref {}))
(deftype InMemoryGardenRepo []
  garden/GardenRepo
  (list-gardens [this] (for [[id g] @gardens] {:id id :name (:name g)}))
  (get-garden [this id] (get @gardens id))
  (add-garden [this g]
    (dosync
     (alter id-cnt inc)
     (alter gardens assoc @id-cnt g))
    @id-cnt)
  (update-garden [repo id g]
    (dosync
     (alter gardens assoc id g)))
  (delete-garden [repo id]
    (dosync
     (alter gardens dissoc @gardens id)
     ))
  plant-desc/PlantDescriptionRepo
  (list-descriptions [repo]
    (for [[id d] @plant-descriptions] {:id id :kind (:kind (:name d))}))
  (get-plant-desc [repo id]
    (get @plant-descriptions id))
  (add-plant-desc [repo desc]
    (dosync
     (alter id-cnt inc)
     (alter plant-descriptions assoc @id-cnt desc))
    @id-cnt)
  (update-plant-desc [repo id desc]
    (dosync
     (alter plant-descriptions assoc id desc)))
  (delete-plant-desc [repo id]
    (dosync
     (alter plant-descriptions dissoc @plant-descriptions id)
     )))

;; Populate garden repo with some data
(defn create-test-repo []
  (dosync
   (ref-set gardens {})
   (ref-set id-cnt 0))
  (let [repo (InMemoryGardenRepo.)]
    (.add-garden repo (garden/create-garden "Babylon"))
    (.add-garden repo (garden/create-garden "Versailles"))
    (.add-plant-desc repo (plant-desc/create-plant-desc
                           "Apiacea" "Daucus" "Carrot" "Early Nantes"))
    (.add-plant-desc repo (plant-desc/create-plant-desc
                           "Apiacea" "Daucus" "Carrot" "Amsterdam"))
    (.add-plant-desc repo (plant-desc/create-plant-desc
                           "Poaceae" "Zea" "Mays" "Ashworth"))
    repo))

(defn setup-test-repos []
  (let [r (create-test-repo)]
    (garden/set-garden-repo nil)
    (garden/set-garden-repo r)
    (plant-desc/set-plant-desc-repo nil)
    (plant-desc/set-plant-desc-repo r)))
