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

(ns spira.service.service
  (:require [compojure.core :refer :all]
            [spira.dm.garden :as garden]
            [spira.dm.test-model :refer :all]
            [spira.service.views :refer :all]
            [hiccup.middleware :refer [wrap-base-url]]
            [cheshire.core :as json]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

;; Change set this to a propper repo
(defn req-garden [id]
  "Respond to /garden/id GET request"
  (let [g (.get-garden (garden/get-garden-repo) id)]
         (if (nil? g) :bad-req g)))

(defn req-garden-list []
  "Respond to /garden GET request"
  (map :name (vals (.list-gardens (garden/get-garden-repo)))))

(defn create-garden [params]
  "Create garden request"
  (let [r (garden/get-garden-repo)
        new-garden (garden/create-garden (:name params))]
    (.add-garden r new-garden)))

(defn update-garden [id params]
  "Update garden request"
  (let [r (garden/get-garden-repo)
        new-garden (garden/create-garden (:name params))]
    (.update-garden r id new-garden)))

(defn delete-garden [id]
  "Delete garden request"
  (let [r (garden/get-garden-repo)]
    (.delete-garden r id)))
