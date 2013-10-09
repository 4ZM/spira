;; Copyright (C) 2013 Anders Sundman <anders@4zm.org>
;;
;; This program is free software: you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
;; GNU General Public License for more details.
;;
;; You should have received a copy of the GNU General Public License
;; along with this program. If not, see <http://www.gnu.org/licenses/>.

(ns spira.dm.test-model
  (:require [spira.dm.garden :as garden]
            [spira.dm.species :as species]
            [spira.dm.seeding :as seeding]
            [spira.dm.test-util :as test-util]
            [spira.util :as util])
  
  (:import [spira.dm.species PlantSpecies])
  (:import [spira.dm.garden Garden])
  (:import [spira.dm.seeding Seeding]))

;; This class creates and populates a small domain model that can be
;; used in unit tests.

;; Garden repo
(def gardens (atom '()))
(deftype InMemoryGardenRepo []
  garden/GardenRepo
  (list-gardens [this] @gardens)
  (get-garden [this name]
    (util/find-first #(= name (:name %)) (.list-gardens this)))
  (add-garden [this g] (swap! gardens conj g)))

(defn- create-test-garden []
  (let [garden-repo (InMemoryGardenRepo.)]
    (.add-garden garden-repo (garden/create-garden "Babylon"))
    (.add-garden garden-repo (garden/create-garden "Luxor"))
    garden-repo))
(def test-garden-repo (create-test-garden))

;; Add seedings
(def seeding-1 (seeding/create-seeding (:id babylon)))


;; Species repo
(def species (atom '()))
(deftype InMemorySpeciesRepo []
  species/SpeciesRepo
  (list-species [this] @species)
  (get-species [this name]
    (filter #(= name (:name %)) @species))
  (get-species [this name kind]
    (filter #(= kind (:kind-name %)) (.get-species this name)))
  (add-species [this s] (swap! species conj s)))

(defn- create-test-species []
  (let [species-repo (InMemorySpeciesRepo.)]
    (.add-species species-repo (test-util/create-test-plant "Carrot" "Early Nantes"))
    (.add-species species-repo (test-util/create-test-plant "Carrot" "Amsterdam"))
    (.add-species species-repo (test-util/create-test-plant "Corn" "Ashworth"))
    species-repo))
(def test-species-repo (create-test-species))


