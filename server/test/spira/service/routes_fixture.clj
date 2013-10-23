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

(def http-req-ok 200)
(def http-req-bad 400)
(def http-req-not-found 404)

(defn test-req [uri & params]
  {:request-method :get
   :uri uri :headers []
   :params (first params)})

(deftest test-not-found
  (testing "Testing a non existant route"
    (is (= http-req-not-found
           (:status (app-routes (test-req "/api/is/not/a/valid/route")))))
    ))

(deftest test-garden-req-list
  (testing "Testing the /api/garden list route"
    (is (= http-req-ok (:status (app-routes (test-req "/api/garden")))))
    ))

(deftest test-garden-req
  (testing "Testing the /api/garden/id route"
    (is (= http-req-ok (:status (app-routes (test-req "/api/garden/1")))))
    (is (= http-req-bad (:status (app-routes (test-req "/api/garden/100000")))))
    ))
