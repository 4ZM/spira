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

(ns spira.service.service-fixture
  (:require [clojure.test :refer :all]
            [spira.dm.garden :as garden]
            [spira.service.service :refer :all]
            [spira.dm.test-model :refer :all]
            ))

;; Make sure we setup the dm test repos before each test
(defn reset-repo-fixture [f]
  (setup-test-repos)
  (f))
(use-fixtures :each reset-repo-fixture)

(deftest test-req-garden-list
  (testing "Testing response to the gardens request")
  (let [repo (garden/get-garden-repo)
        garden-id (.add-garden repo (garden/create-garden "Keukenhof"))]
    (is (some #(= % "Keukenhof") (req-garden-list)))
    ))

(deftest test-req-garden
  (testing "Testing response to the gardens request")
  (let [repo (garden/get-garden-repo)
        garden-id (.add-garden repo (garden/create-garden "Keukenhof"))]
    (is (= "Keukenhof" (:name (req-garden garden-id))))
    (is (= :bad-req (req-garden "NoSuchGarden")))
    ))

(deftest test-create-garden
  (testing "Testing garden creation")
  ;; test goes here
  )

(deftest test-update-garden
  (testing "Testing garden modification")
  ;; test goes here
  )

(deftest test-delete-garden
  (testing "Testing garden removal")
  ;; test goes here
  )
