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

(ns spira.service.service-fixture
  (:require [clojure.test :refer :all]
            [spira.service.service :refer :all]
            [spira.dm.test-model :refer :all]
            ))

(deftest test-gardens
  (testing "Testing response to the gardens grequest")
  (with-redefs [garden-repo test-garden-repo]
    (is (some #(= % "Luxor") (map :name (req-gardens))))
    ))

(deftest test-garden-req
  (testing "Testing response to the gardens grequest")
  (with-redefs [garden-repo test-garden-repo]
    (is (= "Luxor" (:name (req-gardens "Luxor"))))
    (is (= :bad-req (req-gardens "NoSuchGarden")))
    ))
