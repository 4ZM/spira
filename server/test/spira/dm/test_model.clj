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
            [spira.dm.in-memory-repo :as imr]
            [spira.core.util :as util]))
;; This class creates and populates a small domain model that can be
;; used in unit tests.

(defn create-test-repo []
  (imr/reset-repo!)
  (let [repo (imr/->InMemoryRepo)]
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
