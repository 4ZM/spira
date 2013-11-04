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

(ns spira.rest-adapter.routes
  (:require [spira.core.util :as util]
            [spira.services.garden-service :as gs]
            [spira.services.plant-desc-service :as pds]
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

  ;; /api/garden
  (GET "/api/garden/:id" [id]
       (json-response (gs/req-garden (util/parse-uint id))))
  (GET "/api/garden" []
       (json-response (gs/req-garden-list)))
  (POST "/api/garden" [& params]
        (json-response (gs/create-garden params)))
  (PUT "/api/garden/:id" [id & params]
       (json-response (gs/update-garden (util/parse-uint id) params)))
  (DELETE "/api/garden/:id" [id]
          (json-response (gs/delete-garden (util/parse-uint id))))

  ;; /api/plantdesc
  (GET "/api/plantdesc/:id" [id]
       (json-response (pds/req-plant-desc (util/parse-uint id))))
  (GET "/api/plantdesc" []
       (json-response (pds/req-plant-desc-list)))
  (POST "/api/plantdesc" [& params]
        (json-response (pds/create-plant-desc params)))
  (PUT "/api/plantdesc/:id" [id & params]
       (json-response (pds/update-plant-desc (util/parse-uint id) params)))
  (DELETE "/api/plantdesc/:id" [id]
          (json-response (pds/delete-plant-desc (util/parse-uint id))))

  ;; fallback
  (route/not-found "Not Found"))


(def app
  (-> (handler/api app-routes)))