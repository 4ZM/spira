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

(ns spira.dm.seeding
  [:require [spira.util :as util]])

;; A seeding is a plant or a collection of plats treated as a unit
;; in the calendar context.
(defrecord Seeding
    [
     guid
     species-id
     ])

;; Seeding factory
(defn create-seeding [species-id]
  (Seeding. (util/uuid) species-id))

