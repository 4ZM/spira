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

(ns spira.dm.plant-desc)

(defrecord PlantName [family genus species kind])
(defrecord PlantDescription [name])

;; Garden factory
(defn create-plant-desc [family genus species kind]
  (->PlantDescription (->PlantName family genus species kind)))

;; Plant repository
(defprotocol PlantDescriptionRepo
  "Repository of plant information. Supports CRUD style operations."
  (list-descriptions [repo]
    "Get a seq of all plants in the repo. {:id x :species x :kind y}")
  (get-plant-desc [repo id]
    "Get a specific plant description by it's id or nil if not
    found.")
  (add-plant-desc [repo desc]
    "Add a new descriptionn to the repository. The added
    descriptions's id is returned.")
  (update-plant-desc [repo id desc]
    "Modify the description identified by id. Return true if the
    description was successfully updated.")
  (delete-plant-desc [repo id]
    "Remove a descriptions from the repository. Return true if the
    description was successfully removed."))

;; The plant description repo singleton
(def plant-desc-repo (atom nil))
(defn get-plant-desc-repo []
  (assert @plant-desc-repo "Request before initialization")
  @plant-desc-repo)
(defn set-plant-desc-repo [new]
  (assert (or (nil? new) (nil? @plant-desc-repo)) "Can't reset to anything but nil")
  (reset! plant-desc-repo new))
