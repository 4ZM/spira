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

(ns spira.services.plant-desc-service
  (:require [spira.dm.plant-desc :as pd]))

(defn req-plant-desc [id]
  "Respond to plant desc with id request"
  (let [r (pd/get-plant-desc-repo)]
    (or (-> r (pd/get-plant-desc id)) :bad-req)))

(defn req-plant-desc-list []
  "Respond to plant desc list request"
  (-> (pd/get-plant-desc-repo) (pd/list-descriptions)))

(defn create-plant-desc [params]
  "Create plant desc request"
  (let [r (pd/get-plant-desc-repo)
        new-desc (pd/create-plant-desc (:family params)
                                       (:genus params)
                                       (:species params)
                                       (:kind params))]
    (-> r (pd/add-plant-desc new-desc))))

(defn update-plant-desc [id params]
  "Update plant desc request"
  (let [r (pd/get-plant-desc-repo)
        new-desc (pd/create-plant-desc (:family params)
                                       (:genus params)
                                       (:species params)
                                       (:kind params))]
    (or (-> r (pd/update-plant-desc id new-desc)) :bad-req)))

(defn delete-plant-desc [id]
  "Delete plant desc  request"
  (let [r (pd/get-plant-desc-repo)]
    (or (-> r (pd/delete-plant-desc id)) :bad-req)))
