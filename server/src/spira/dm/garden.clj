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
  (->Garden name []))

(defn add-seeding [g s]
  "Add a seeding to a garden"
  (update-in g [:seedings] conj s))

(defn- rm-seeding-impl [seedings sid]
  "Given a list of seedings, remove the one with a matching id"
  (remove (fn [{id :id}] (= id sid)) seedings))

(defn rm-seeding [g id]
  "Remove a seeding from a garden"
  (update-in g [:seedings] rm-seeding-impl id))

;; Garden repository
(defprotocol GardenRepo
  "Repository of Gardens. Supports CRUD style operations."
  (list-gardens [_]
    "Get a seq of all gardens in the repo. {:id x :name somename}")
  (get-garden [_ id]
    "Get a specific garden by it's id or nil if not found")
  (add-garden [_ garden]
    "Add a new garden to the repository. The added garden's id is
    returned.")
  (update-garden [_ id garden]
    "Modify the garden identified by id. Return true if the garden was
    successfully updated.")
  (delete-garden [_ id]
    "Remove a garden from a repository. Return true if the garden was
    successfully removed."))

;; The garden repo singleton
(def garden-repo (atom nil))
(defn get-garden-repo []
  (assert @garden-repo "Request before initialization")
  @garden-repo)
(defn set-garden-repo [new]
  (assert (or (nil? new) (nil? @garden-repo)) "Can't reset to anything but nil")
  (reset! garden-repo new))
