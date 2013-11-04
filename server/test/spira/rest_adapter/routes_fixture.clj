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

(ns spira.rest-adapter.routes-fixture
  (:require [midje.sweet :refer :all]
            [spira.dm.test-model :refer :all]
            [spira.services.garden-service :as gs]
            [spira.services.plant-desc-service :as pds]
            [spira.rest-adapter.routes :refer :all]))

(defn- test-req [method uri & params]
  {:request-method method
   :uri uri :headers []
   :params (first params)})

(defn- test-resp [status & data]
  "Helper to create mock responses"
  (if (nil? data) {:status status} {:status status :data data}))

(facts "about invalid requests"
  (fact "Invalid routes give error status"
    (:status (app-routes (test-req :get "/api/is/not/a/valid/route"))) =>
    (:not-found http-status)))

;; api/garden routes ---

(facts "about list requests"
  (fact "/api/garden requests list"
    (let [req (test-req :get "/api/garden")]
      (:status (app-routes req)) => (:ok http-status)
      (provided (gs/req-garden-list) => (test-resp :ok []) :times 1))))

(facts "about garden get requests"
  (fact "/api/garden/id gets garden"
    (:status (app-routes (test-req :get "/api/garden/1"))) => (:ok http-status)
    (provided (gs/req-garden 1) => (test-resp :ok {}) :times 1))
  (fact "/api/garden/id with wrong id reports error"
    (:status (app-routes (test-req :get "/api/garden/1"))) => (:bad-req http-status)
    (provided (gs/req-garden 1) => (test-resp :bad-req) :times 1)))

(facts "about garden create requests"
  (fact "/api/garden post creates garden"
    (let [params {:name "torture"}
          req (test-req :post "/api/garden" params)]
      (:status (app-routes req)) => (:created http-status)
      (provided (gs/create-garden params) =>
                (test-resp :created {:id 1}) :times 1)))

  (fact "/api/garden post bad data gives error"
    (let [bad-params {:wierd "param"}
          req (test-req :post "/api/garden" bad-params)]
      (:status (app-routes req)) => (:bad-req http-status)
      (provided (gs/create-garden bad-params) =>
                (test-resp :bad-req) :times 1))))

(facts "about garden update requests"
  (fact "/api/garden/id put request updates"
    (let [params {:name "torture"}
          req (test-req :put "/api/garden/1" params)]
      (:status (app-routes req)) => (:ok http-status)
      (provided (gs/update-garden 1 params) =>
                (test-resp :ok) :times 1))))

(facts "about garden delete requests"
  (fact "/api/garden/id delete request deletes"
    (let [req (test-req :delete "/api/garden/1")]
      (:status (app-routes req)) => (:ok http-status)
      (provided (gs/delete-garden 1) =>
                (test-resp :ok) :times 1))))

;; api/plantdesc routes ---


(facts "about list requests"
  (fact "/api/plantdesc requests list"
    (let [req (test-req :get "/api/plantdesc")]
      (:status (app-routes req)) => (:ok http-status)
      (provided (pds/req-plant-desc-list) => (test-resp :ok []) :times 1))))

(facts "about plant description get requests"
  (fact "/api/plantdesc/id gets plant description"
    (:status (app-routes (test-req :get "/api/plantdesc/1"))) => (:ok http-status)
    (provided (pds/req-plant-desc 1) => (test-resp :ok {}) :times 1))
  (fact "/api/plantdesc/id with wrong id reports error"
    (:status (app-routes (test-req :get "/api/plantdesc/1"))) => (:bad-req http-status)
    (provided (pds/req-plant-desc 1) => (test-resp :bad-req) :times 1)))

(facts "about plant description create requests"
  (fact "/api/plantdesc post creates plant description"
    (let [params {:family "f" :genus "g" :species "s" :kind "k"}
          req (test-req :post "/api/plantdesc" params)]
      (:status (app-routes req)) => (:created http-status)
      (provided (pds/create-plant-desc params) =>
                (test-resp :created {:id 1}) :times 1)))

  (fact "/api/plantdesc post bad data gives error"
    (let [bad-params {:wierd "param"}
          req (test-req :post "/api/plantdesc" bad-params)]
      (:status (app-routes req)) => (:bad-req http-status)
      (provided (pds/create-plant-desc bad-params) =>
                (test-resp :bad-req) :times 1))))

(facts "about plant description update requests"
  (fact "/api/plantdesc/id put request updates"
    (let [params {:family "f" :genus "g" :species "s" :kind "k"}
          req (test-req :put "/api/plantdesc/1" params)]
      (:status (app-routes req)) => (:ok http-status)
      (provided (pds/update-plant-desc 1 params) =>
                (test-resp :ok) :times 1))))

(facts "about plant description delete requests"
  (fact "/api/plantdesc/id delete request deletes"
    (let [req (test-req :delete "/api/plantdesc/1")]
      (:status (app-routes req)) => (:ok http-status)
      (provided (pds/delete-plant-desc 1) =>
                (test-resp :ok) :times 1))))
