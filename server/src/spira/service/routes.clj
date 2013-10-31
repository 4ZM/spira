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

(ns spira.service.routes
  (:require [spira.core.util :as util]
            [spira.service.service :as service]
            [ring.middleware.params :refer [wrap-params]]
            [cheshire.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(def http-status
  {:ok 200
   :created 201
   :bad-req 400
   :not-found 404
   })

(defn json-response [{status :status data :data}]
  "Create a json HTTP response from a status keyword and a map"
  (let [status-code (status http-status)
        resp {:status status-code
              :headers {"Content-Type" "application/json"}}]
    (if (nil? data)
      resp
      (assoc resp :body (json/generate-string data)))))

;; The CRUD REST api follows the following schema:
;;
;; GET       /api/foo         -> list all records
;; GET       /api/foo/<id>    -> view record
;; POST      /api/foo         -> create new record
;; PUT       /api/foo/<id>    -> update record
;; DELETE    /api/foo/<id>    -> delete record

(defroutes app-routes
  (GET "/api/garden/:id" [id]
       (json-response (service/req-garden (util/parse-uint id))))
  (GET "/api/garden" []
       (json-response (service/req-garden-list)))
  (POST "/api/garden" [& params]
        (json-response (service/create-garden params)))
  (PUT "/api/garden/:id" [id & params]
       (json-response (service/update-garden (util/parse-uint id) params)))
  (DELETE "/api/garden/:id" [id]
          (json-response (service/delete-garden (util/parse-uint id))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)))
