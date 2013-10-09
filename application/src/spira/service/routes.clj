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

(ns spira.webapp.routes
  (:use
   [compojure.core]
   [spira.webapp.views]
   [hiccup.middleware :only (wrap-base-url)])
  (:require
   [cheshire.core :as json]
   [compojure.route :as route]
   [compojure.handler :as handler]
   [compojure.response :as response]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
      :body (json/generate-string data)})

(defroutes app-routes
  (GET "/" [] (index-page))
  (GET "/api/fu" [arg] (json-response {:wicked arg :kickass "ehh"}))
  (route/resources "/")
  (route/not-found (not-found-page)))

(def app
  (-> (handler/site app-routes)
;;      (wrap-params)
;;      (wrap-content-type)
      (wrap-base-url)))
