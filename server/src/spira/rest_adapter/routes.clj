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
            [spira.rest-adapter.garden-service :as gs]
            [spira.rest-adapter.plant-service :as ps]
            [spira.rest-adapter.util :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]))


;; The CRUD REST api follows the following schema:
;;
;; GET       /api/foo         -> list all records
;; GET       /api/foo/<id>    -> view record
;; POST      /api/foo         -> create new record
;; PUT       /api/foo/<id>    -> update record
;; DELETE    /api/foo/<id>    -> delete record

(defn app-routes [{garden-repo :garden-repo plant-repo :plant-repo}]
  (routes

   ;; /api/garden
   (GET "/api/garden/:id" [id]
        (json-response
         (gs/req-garden garden-repo (util/parse-uint id))))
   (GET "/api/garden" []
        (json-response
         (gs/req-garden-list garden-repo)))
   (POST "/api/garden" {body :body}
         (json-response
          (gs/create-garden garden-repo body)))
   (PUT "/api/garden/:id" [id & params]
        (json-response
         (gs/update-garden garden-repo (util/parse-uint id) params)))
   (DELETE "/api/garden/:id" [id]
           (json-response
            (gs/delete-garden garden-repo (util/parse-uint id))))

   ;; /api/species
   (GET "/api/species/:id" [id]
         (json-response
          (ps/req-species plant-repo (util/parse-uint id))))
   (GET "/api/species" []
        (json-response
         (ps/req-species plant-repo)))
   (POST "/api/species" {body :body}
         (json-response
          (ps/create-species plant-repo body)))
   (PUT "/api/species/:id" {body :body}
        (json-response
         (ps/update-species plant-repo body)))
   (DELETE "/api/species/:id" [id]
           (json-response
            (ps/delete-species plant-repo (util/parse-uint id))))

   ;; /kinds
   (GET "/api/species/:sid/kinds" [sid]
        (json-response
         (ps/req-kinds plant-repo (util/parse-uint sid))))
   (POST "/api/species/:sid/kinds" {body :body}
         (json-response
          (ps/create-kind plant-repo body)))
   (PUT "/api/species/:sid/kinds/:kid" {body :body}
        (json-response
         (ps/update-kind plant-repo body)))
   (DELETE "/api/species/:sid/kinds/:kid" [sid kid]
           (json-response
            (ps/delete-kind plant-repo (util/parse-uint kid))))

   ;; fallback
   (route/not-found "Not Found")
  ))
