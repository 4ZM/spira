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

(ns spira.core.system
  (:require [spira.dm.garden :as garden]
            [spira.dm.plant :as p]
            [spira.datomic-adapter.repo :as da]
            [spira.datomic-adapter.data :as data]))

;; A representation of the applications total top level state.
(defrecord SystemState [garden-repo plant-repo])

(defn create-system-state [uri]
  "Create a light weight development state"
  (data/create-test-db uri)
  (let [gr (da/create-garden-repo uri)
        pr (da/create-plant-repo uri)]
    (->SystemState gr pr)))

(def test-uri "datomic:mem://spira")

(defn dev-system []
  "Create a light weight development state"
  (let [s (create-system-state test-uri)]
    (data/populate-gardens! (:garden-repo s))
    (data/populate-plant-repo! (:plant-repo s))
    s))

