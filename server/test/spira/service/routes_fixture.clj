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

(ns spira.service.routes-fixture
  (:require [clojure.test :refer :all]
            [spira.dm.test-model :refer :all]
            [spira.service.routes :refer :all]))

;; Make sure we setup the dm test repos before each test
(defn reset-repo-fixture [f]
  (setup-test-repos)
  (f))
(use-fixtures :each reset-repo-fixture)

(def http-req-ok 200)
(def http-req-bad 400)
(def http-req-not-found 404)

(defn test-req [method uri & params]
  {:request-method method
   :uri uri :headers []
   :params (first params)})

(deftest test-not-found
  (testing "Testing a non existant route"
    (is (= http-req-not-found
           (:status (app-routes (test-req :get "/api/is/not/a/valid/route")))))
    ))

(deftest test-garden-req-list
  (testing "Testing the /api/garden list route : get list"
    (is (= http-req-ok (:status (app-routes (test-req :get "/api/garden")))))
    ))

(deftest test-garden-req
  (testing "Testing the /api/garden/id route : get"
    (is (= http-req-ok (:status (app-routes (test-req :get "/api/garden/1")))))
    (is (= http-req-bad (:status (app-routes (test-req :get "/api/garden/100000")))))
    ))

(deftest test-garden-create
  (testing "Testing the /api/garden post route : create"
    (is (= http-req-ok
           (:status (app-routes (test-req :post "/api/garden" {:name "torture"})))))
    ))

(deftest test-garden-update
  (testing "Testing the /api/garden/id put route : update"
    (is (= http-req-ok
           (:status (app-routes (test-req :put "/api/garden/1" {:name "torture"})))))
    ))

(deftest test-garden-delete
  (testing "Testing the /api/garden/id delete route : delete"
    (is (= http-req-ok (:status (app-routes (test-req :delete "/api/garden/1")))))
    ))
