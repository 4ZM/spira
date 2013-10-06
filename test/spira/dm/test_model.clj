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
            [spira.dm.test-util :as util])
  
  (:import [spira.dm.species PlantSpecies])
  (:import [spira.dm.garden Garden])
  (:import [spira.dm.seeding Seeding]))

;; This class creates and populates a small domain model that can be
;; used in unit tests.

;; Plants
(def carrot-early-nantes (util/create-test-plant "Carrot" "Early Nantes"))
(def carrot-amsterdam (util/create-test-plant "Carrot" "Amsterdam"))
(def corn-ashworth (util/create-test-plant "Corn" "Ashworth"))

;; Gardens
(def babylon (garden/create-garden "babylon"))
(def luxor (garden/create-garden "luxor"))

;; Add seedings
(def seeding-1 (seeding/create-seeding (:id babylon)))
