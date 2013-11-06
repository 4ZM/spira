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

(ns spira.rest-adapter.util
  (:require [cheshire.core :as json]))

(def http-status
  {:ok 200
   :created 201
   :bad-req 400
   :not-found 404
   })

(defn response [status & data]
  "Helper to create service responses"
  (if (nil? data) {:status status} {:status status :data (first data)}))

(defn json-response [{status :status data :data}]
  "Create a json HTTP response from a status keyword and a map"
  (let [status-code (status http-status)
        resp {:status status-code
              :headers {"Content-Type" "application/json"}}]
    (if (nil? data) resp (assoc resp :body (json/generate-string data)))))
