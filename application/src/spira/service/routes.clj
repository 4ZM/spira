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

(ns spira.service.routes
  (:require [spira.service.service :as service]
   [spira.service.views :refer :all]
   [ring.middleware.params :refer [wrap-params]]
   [cheshire.core :as json]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [compojure.handler :as handler]
   [compojure.response :as response]))

(defn json-response [data & [status]]
  (let [status (or status (if (= data :bad-req) 400) 200)]
   {:status status
    :headers {"Content-Type" "application/json"}
    :body (if (= status 200) (json/generate-string data))}))

(defroutes app-routes
  (GET "/api/gardens" [id] (let [r (if (nil? id) (service/req-gardens) (service/req-gardens id))]  (json-response r))) 
  (route/not-found "Page not found"))

(def app
  (-> (handler/site app-routes)
      wrap-params))
