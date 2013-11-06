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

(ns spira.rest-adapter.garden-service
  (:require [spira.dm.garden :as garden]
            [spira.rest-adapter.util :refer :all]))

(defn req-garden [repo id]
  "Respond to garden with id request"
  (let [res (-> repo (garden/get-garden id))]
    (if (nil? res) (response :bad-req) (response :ok res))))

(defn req-garden-list [repo]
  "Respond to garden list request"
  (response :ok (-> repo (garden/list-gardens))))

(defn create-garden [repo params]
  "Create garden request"
  (let [new-garden (garden/create-garden (:name params))]
    (response :created (-> repo (garden/add-garden new-garden)))))

(defn update-garden [repo id params]
  "Update garden request"
  (let [new-garden (garden/create-garden (:name params))
        res (-> repo (garden/update-garden id new-garden))]
    (if res (response :ok res) (response :bad-req))))

(defn delete-garden [repo id]
  "Delete garden request"
  (let [res (-> repo (garden/delete-garden id))]
    (if res (response :ok) (response :bad-req))))
