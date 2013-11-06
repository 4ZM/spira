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

(ns spira.dm.in-memory-repo
  (:require [spira.dm.garden :as garden]
            [spira.dm.plant-desc :as plant-desc]
            [spira.dm.seeding :as seeding]
            [spira.core.util :as util]))


(defrecord MemoryGardenRepo [id-cnt gardens]
  garden/GardenRepo
  (list-gardens [_] (for [[id g] @gardens] {:id id :name (:name g)}))
  (get-garden [_ id] (get @gardens id))
  (add-garden [_ g]
    (dosync
     (alter id-cnt inc)
     (alter gardens assoc @id-cnt g))
    @id-cnt)
  (update-garden [_ id g]
    (dosync
     (alter gardens assoc id g)))
  (delete-garden [_ id]
    (dosync
     (alter gardens dissoc @gardens id))))

(defn memory-garden-repo []
  (->MemoryGardenRepo (ref 0) (ref {})))

(defrecord MemoryPlantDescriptionRepo [id-cnt plant-descriptions]
  plant-desc/PlantDescriptionRepo
  (list-descriptions [_]
    (for [[id d] @plant-descriptions] {:id id :kind (:kind (:name d))}))
  (get-plant-desc [_ id]
    (get @plant-descriptions id))
  (add-plant-desc [_ desc]
    (dosync
     (alter id-cnt inc)
     (alter plant-descriptions assoc @id-cnt desc))
    @id-cnt)
  (update-plant-desc [_ id desc]
    (dosync
     (alter plant-descriptions assoc id desc)))
  (delete-plant-desc [_ id]
    (dosync
     (alter plant-descriptions dissoc @plant-descriptions id)
     )))

(defn memory-plant-description-repo []
  (->MemoryPlantDescriptionRepo (ref 0) (ref {})))

