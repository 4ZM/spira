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
  (:require [hiccup.middleware :refer [wrap-base-url]]
   [spira.service.service :as service]
   [spira.service.views :refer :all]
   [cheshire.core :as json]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [compojure.handler :as handler]
   [compojure.response :as response]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
      :body (json/generate-string data)})

(defroutes app-routes
  (GET "/" [] (index-page))
  (GET "/api/gardens" [] (json-response (service/req-gardens)))
  (route/resources "/")
  (route/not-found (not-found-page)))

(def app
  (-> (handler/site app-routes)
;;      (wrap-params)
;;      (wrap-content-type)
      (wrap-base-url)))
