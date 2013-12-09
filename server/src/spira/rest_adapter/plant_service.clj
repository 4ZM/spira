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

(ns spira.rest-adapter.plant-service
  (:require [spira.dm.plant :as p]
            [spira.rest-adapter.util :refer :all]))

(defn req-species
  ([repo]
     "Respond to species list request"
     (response :ok (p/species repo)))
  ([repo id]
     "Respond to specific species request with id"
     (let [res (p/species repo id)]
       (if (nil? res) (response :bad-req) (response :ok res)))))

(defn create-species [repo params]
  "Create species request"
  (let [new-species (p/create-species (:name params)
                                      (:family params)
                                      (:genus params)
                                      (:description params))]
    (response :created (p/add-species repo new-species))))

(defn update-species [repo id params]
  "Update plant desc request"
  (let [new-species (p/create-species (:id id)
                                      (:name params)
                                      (:family params)
                                      (:genus params)
                                      (:descrption params))]
    (p/add-species repo new-species)
    (response :ok)))

(defn delete-species [repo id]
  "Delete species request"
  (let [success (-> repo (p/delete-species id))]
    (response (if success :ok :bad-req))))

(defn req-kinds
  [repo sid]
  "Respond to kind list request"
  (response :ok (p/kinds repo sid)))
