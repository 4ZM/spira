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

(ns spira.rest-adapter.plant-desc-service
  (:require [spira.dm.plant-desc :as pd]
            [spira.rest-adapter.util :refer :all]))

(defn req-plant-desc [repo id]
  "Respond to plant desc with id request"
  (let [res (-> repo (pd/get-plant-desc id))]
    (if (nil? res) (response :bad-req) (response :ok res))))

(defn req-plant-desc-list [repo]
  "Respond to plant desc list request"
  (response :ok (-> repo (pd/list-descriptions))))

(defn create-plant-desc [repo params]
  "Create plant desc request"
  (let [new-desc (pd/create-plant-desc (:family params)
                                       (:genus params)
                                       (:species params)
                                       (:kind params))]
    (response :created (-> repo (pd/add-plant-desc new-desc)))))

(defn update-plant-desc [repo id params]
  "Update plant desc request"
  (let [new-desc (pd/create-plant-desc (:family params)
                                       (:genus params)
                                       (:species params)
                                       (:kind params))
        success (-> repo (pd/update-plant-desc id new-desc))]
    (response (if success :ok :bad-req))))

(defn delete-plant-desc [repo id]
  "Delete plant desc  request"
  (let [success (-> repo (pd/delete-plant-desc id))]
    (response (if success :ok :bad-req))))
