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
  (let [g-repo (imr/memory-garden-repo)
        pd-repo (imr/memory-plant-description-repo)]
    (-> g-repo (.add-garden (garden/create-garden "Babylon")))
    (-> g-repo (.add-garden (garden/create-garden "Versailles")))
    (-> pd-repo (.add-plant-desc (plant-desc/create-plant-desc
                                  "Apiacea" "Daucus" "Carrot" "Early Nantes")))
    (-> pd-repo (.add-plant-desc (plant-desc/create-plant-desc
                                  "Apiacea" "Daucus" "Carrot" "Amsterdam")))
    (-> pd-repo (.add-plant-desc (plant-desc/create-plant-desc
                                  "Poaceae" "Zea" "Mays" "Ashworth")))
    {:garden g-repo
     :plant-desc pd-repo}))
