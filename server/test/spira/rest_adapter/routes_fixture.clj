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
            [spira.core.system :as sys]
            [spira.rest-adapter.util :refer :all]
            [spira.rest-adapter.garden-service :as gs]
            [spira.rest-adapter.plant-service :as ps]
            [spira.rest-adapter.routes :refer :all]))

(defn- test-req [method uri & params]
  {:request-method method
   :uri uri :headers []
   :params (first params)})

(defn- test-post [uri body]
  (let [r (test-req :post uri)]
    (assoc r :body body)))

(defn- test-state []
  (sys/->SystemState :gr :pr))

(facts "about invalid requests"
  (fact "Invalid routes give error status"
    (:status ((app-routes {}) (test-req :get "/api/is/not/a/valid/route"))) =>
    (:not-found http-status)))

;; api/garden routes ---

(facts "about list requests"
  (fact "/api/garden requests list"
    (let [req (test-req :get "/api/garden")
          state (test-state)]
      (:status ((app-routes state) req)) => (:ok http-status)
      (provided (gs/req-garden-list :gr) => (response :ok []) :times 1))))

(facts "about garden get requests"
  (fact "/api/garden/id gets garden"
    (let [state (test-state)]
      (:status ((app-routes state)
                (test-req :get "/api/garden/1"))) => (:ok http-status)
      (provided (gs/req-garden :gr 1) => (response :ok {}) :times 1))
    (fact "/api/garden/id with wrong id reports error"
      (let [state (test-state)]
        (:status ((app-routes state)
                  (test-req :get "/api/garden/1"))) => (:bad-req http-status)
        (provided (gs/req-garden :gr 1) => (response :bad-req) :times 1)))))

(facts "about garden create requests"
  (fact "/api/garden post creates garden"
    (let [body {:name "torture"}
          req (test-post "/api/garden" body)
          state (test-state)]
      (:status ((app-routes state) req)) => (:created http-status)
      (provided (gs/create-garden :gr body) =>
                (response :created {:id 1}) :times 1)))

  (fact "/api/garden post bad data gives error"
    (let [bad-params {:wierd "param"}
          req (test-post "/api/garden" bad-params)
          state (test-state)]
      (:status ((app-routes state) req)) => (:bad-req http-status)
      (provided (gs/create-garden :gr bad-params) =>
                (response :bad-req) :times 1))))

(facts "about garden update requests"
  (fact "/api/garden/id put request updates"
    (let [params {:name "torture"}
          req (test-req :put "/api/garden/1" params)
          state (test-state)]
      (:status ((app-routes state) req)) => (:ok http-status)
      (provided (gs/update-garden :gr 1 params) =>
                (response :ok) :times 1))))

(facts "about garden delete requests"
  (fact "/api/garden/id delete request deletes"
    (let [req (test-req :delete "/api/garden/1")
          state (test-state)]
      (:status ((app-routes state) req)) => (:ok http-status)
      (provided (gs/delete-garden :gr 1) =>
                (response :ok) :times 1))))

;; api/plantdesc routes ---


(facts "about list requests"
  (fact "/api/species requests list"
    (let [req (test-req :get "/api/species")
          state (test-state)]
      (:status ((app-routes state) req)) => (:ok http-status)
      (provided (ps/req-species :pr) => (response :ok []) :times 1))))

(facts "about plant description get requests"
  (fact "/api/species/id gets plant description"
    (let [state (test-state)]
      (:status ((app-routes state) (test-req :get "/api/species/1"))) => (:ok http-status)
      (provided (ps/req-species :pr 1) => (response :ok {}) :times 1)))
  (fact "/api/species/id with wrong id reports error"
    (let [state (test-state)]
      (:status ((app-routes state) (test-req :get "/api/species/1"))) => (:bad-req http-status)
      (provided (ps/req-species :pr 1) => (response :bad-req) :times 1))))

(facts "about plant description create requests"
  (fact "/api/species post creates plant description"
    (let [body {:name "sn" :family "f" :genus "g" :description "d"}
          req (test-post "/api/species" body)
          state (test-state)]
      (:status ((app-routes state) req)) => (:created http-status)
      (provided (ps/create-species :pr body) =>
                (response :created {:id 1}) :times 1)))

  (fact "/api/species post bad data gives error"
    (let [bad-params {:wierd "param"}
          req (test-post "/api/species" bad-params)
          state (test-state)]
      (:status ((app-routes state) req)) => (:bad-req http-status)
      (provided (ps/create-species :pr bad-params) =>
                (response :bad-req) :times 1))))

(facts "about plant description update requests"
  (fact "/api/species/id put request updates"
    (let [params {:name "sn" :family "f" :genus "g" :description "d"}
          req (test-req :put "/api/species/1" params)
          state (test-state)]
      (:status ((app-routes state) req)) => (:ok http-status)
      (provided (ps/update-species :pr 1 params) =>
                (response :ok) :times 1))))

(facts "about plant description delete requests"
  (fact "/api/species/id delete request deletes"
    (let [req (test-req :delete "/api/species/1")
          state (test-state)]
      (:status ((app-routes state) req)) => (:ok http-status)
      (provided (ps/delete-species :pr 1) =>
                (response :ok) :times 1))))


;; Full regression test

(def json-header {"Content-Type" "application/json"})

(facts "about the whole call chain"
  (fact "empty json array on empty garden list request"
    (let [state (sys/create-system-state sys/test-uri)
          list-req (test-req :get "/api/garden")
          routes (app-routes state)]
      (routes list-req) =>
      {:status (:ok http-status)
       :headers json-header
       :body "[]"})))
