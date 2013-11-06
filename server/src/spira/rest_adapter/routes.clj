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
            [spira.rest-adapter.plant-desc-service :as pds]
            [spira.dm.in-memory-repo :as memrepo]
            [spira.rest-adapter.util :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))


;; The CRUD REST api follows the following schema:
;;
;; GET       /api/foo         -> list all records
;; GET       /api/foo/<id>    -> view record
;; POST      /api/foo         -> create new record
;; PUT       /api/foo/<id>    -> update record
;; DELETE    /api/foo/<id>    -> delete record

(defn app-routes [state]
  (compojure.core/routes

   ;; /api/garden
   (GET "/api/garden/:id" [id]
        (json-response
         (gs/req-garden (:garden state) (util/parse-uint id))))
   (GET "/api/garden" []
        (json-response
         (gs/req-garden-list (:garden state))))
   (POST "/api/garden" [& params]
         (json-response
          (gs/create-garden (:garden state) params)))
   (PUT "/api/garden/:id" [id & params]
        (json-response
         (gs/update-garden (:garden state) (util/parse-uint id) params)))
   (DELETE "/api/garden/:id" [id]
           (json-response
            (gs/delete-garden (:garden state) (util/parse-uint id))))

   ;; /api/plantdesc
   (GET "/api/plantdesc/:id" [id]
         (json-response
          (pds/req-plant-desc (:plant-desc state) (util/parse-uint id))))
   (GET "/api/plantdesc" []
        (json-response
         (pds/req-plant-desc-list (:plant-desc state))))
   (POST "/api/plantdesc" [& params]
         (json-response
          (pds/create-plant-desc (:plant-desc state) params)))
   (PUT "/api/plantdesc/:id" [id & params]
        (json-response
         (pds/update-plant-desc (:plant-desc state) (util/parse-uint id) params)))
   (DELETE "/api/plantdesc/:id" [id]
           (json-response
            (pds/delete-plant-desc (:plant-desc state) (util/parse-uint id))))

   ;; fallback
   (route/not-found "Not Found")
  ))

(defn create-app-state []
  {:garden (memrepo/memory-garden-repo)
   :plant-desc (memrepo/memory-plant-description-repo)})

(def app
  (handler/api (app-routes (create-app-state)))
;;  (-> (create-app-state)
  ;;    app-routes
    ;;  handler/api
      )

