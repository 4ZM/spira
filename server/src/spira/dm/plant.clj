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

(ns spira.dm.plant)

(defrecord Species [id name family genus description])
(defrecord Kind [id name species description])

(defn create-species
  ([id name family genus description]
     (->Species id name family genus description))
  ([name family genus description]
     (->Species nil name family genus description)))

(defn create-kind
  ([id name species description]
     (->Kind id name species description))
  ([name species description]
     (->Kind nil name species description)))

;; Plant repository
(defprotocol PlantRepo
  "Repository of plant information."
  (species
    [_]
    [_ id]
    "Get a seq of all species in the repo. If id is provided, get a
    specific species or nil if not found.")

  (add-species [_ species]
    "Add a new species to the repository. The added species id is
    returned. If the species added has an id allready in the db,
    replace the existing entity.")
  (delete-species [_ id]
    "Remove the species.")

  (kinds [_ sid]
    "Get a seq of all kinds related to a specific species.")

  (add-kind [_ kind]
    "Add a new kind to the repository. The added kinds id is returned.
    If the kind added has an id allready in the db, replace the
    existing entity.")
  (delete-kind [_ id]
    "Remove the kind."))
