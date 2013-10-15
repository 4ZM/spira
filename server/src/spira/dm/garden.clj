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

(ns spira.dm.garden)

(defrecord Garden [name seedings])

;; Get an unique id for a garden
(defn id [{name :name}]
  (clojure.string/replace name \space \_))

;; Garden factory
(defn create-garden [name]
  (Garden. name {}))

;; Garden Repository
(defprotocol GardenRepo
  "Repository of Gardens. For reading, writing and searching."
  (list-gardens [repo] "A seq of all gardens.")
  (get-garden [repo name] "Get a specific garden.")
  (add-garden [repo garden] "Add a new garden to the repository."))
