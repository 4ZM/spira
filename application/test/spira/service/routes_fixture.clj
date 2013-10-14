;; Copyright (C) 2013 Anders Sundman <anders@4zm.org>
;;
;; This program is free software: you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
;; GNU General Public License for more details.
;;
;; You should have received a copy of the GNU General Public License
;; along with this program. If not, see <http://www.gnu.org/licenses/>.

(ns spira.service.routes-fixture
  (:require [clojure.test :refer :all]
            [spira.service.routes :refer :all]))

(def http-req-ok 200)
(def http-req-not-found 404)

(defn test-req [uri]
  {:request-method :get
   :uri uri :headers []
   :params []})

(deftest test-not-found
  (testing "Testing a non existant route"
    (is (= http-req-not-found (:status (app-routes (test-req "asdf")))))
    ))

(deftest test-fu
  (testing "Testing the /api/fu route"
    (is (= http-req-ok (:status (app-routes (test-req "/api/fu")))))
    ))
