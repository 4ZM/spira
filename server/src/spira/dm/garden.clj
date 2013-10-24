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

(ns spira.dm.garden)

(defrecord Garden [name seedings])

;; Garden factory
(defn create-garden [name]
  (Garden. name {}))

;; Garden repository
(defprotocol GardenRepo
  "Repository of Gardens. Supports CRUD style operations."
  (list-gardens [repo]
    "Get a seq of all gardens in the repo.")
  (get-garden [repo id]
    "Get a specific garden by it's id.")
  (add-garden [repo garden]
    "Add a new garden to the repository. The added garden's id is returned.")
  (update-garden [repo id garden]
    "Modify the garden identified by id."))

;; The garden repo singleton
(def garden-repo (atom nil))
(defn get-garden-repo []
  (assert @garden-repo "Request before initialization")
  @garden-repo)
(defn set-garden-repo [new]
  (assert (or (nil? new) (nil? @garden-repo)) "Can't reset to anything but nil")
  (reset! garden-repo new))
