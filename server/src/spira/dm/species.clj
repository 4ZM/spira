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

(ns spira.dm.species)

;; Entity object representing a species of plant
;; TODO Indentation of defcrecords?
(defrecord PlantSpecies
    [
     name ; Common name
     sci-name ; Scientific name
     kind-name ; ID
     ])

;; Get an unique id for a species
(defn id [{name :name, kind :kind-name}]
  (clojure.string/replace  (str name "-" kind) \space \_))

;; PlantSpecis Repository

;; TODO Should I keep the "-species" suffix
(defprotocol SpeciesRepo
  "Repository of PlantSpecies. For reading, writing and searching."
  (list-species [repo] "A seq of all species.")
  (get-species [repo name] [repo name kind] "Get a species or a list if several match the name.")
  (add-species [repo species] "Add a new species to the repository."))

;; Species repository
(defprotocol SpeciesRepo
  "Repository of Gardens. Supports CRUD style operations."
  (list-gardens [repo]
    "Get a seq of all gardens in the repo.")
  (get-garden [repo id]
    "Get a specific garden by it's id.")
  (add-garden [repo garden]
    "Add a new garden to the repository. The added garden's id is returned.")
  (update-garden [repo id garden]
    "Modify the garden identified by id.")
  (delete-garden [repo id]
    "Remove a garden from a repository"))
