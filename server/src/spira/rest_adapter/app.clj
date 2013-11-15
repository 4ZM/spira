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

(ns spira.rest-adapter.app
  (:require [compojure.handler :as handler]
            [spira.core.system :as system]
            [ring.middleware.json :as json-mw]
            [spira.rest-adapter.routes :refer :all]))

(defn wrap-json-body [handler]
  (json-mw/wrap-json-body handler {:keywords? true}))

;; Ring entry point
(def app (handler/api (wrap-json-body (app-routes (system/dev-system)))))

