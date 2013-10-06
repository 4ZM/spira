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

(ns spira.dm.species)

;; Entity object representing a species of plant
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
(ns spira.dm.seed-catalog [:use spira.dm.species])
(defn all-species
  "Get a seq of all the plant species"
  []
  nil)
