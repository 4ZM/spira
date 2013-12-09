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

(ns spira.core.util-fixture
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [spira.core.util :refer :all]))

(deftest test-unique-uuid
  (testing "The uuid should return new values on each call."
    (is (not (= (uuid) (uuid))))
    ))

(deftest test-find-first-empty-seq
  (is (= nil (find-first even? '()))))

(deftest test-find-first-no-match
  (is (= nil (find-first even? '(1 3 5 7)))))

(deftest test-find-first-match-found
  (is (= 2 (find-first even? '(2 3 5 7))))
  (is (= 4 (find-first even? '(1 4 5 7))))
  (is (= 8 (find-first even? '(1 3 5 8)))))

(facts "about a2i"
  (fact "converts string to int"
    (parse-uint "23") => 23
    (parse-uint "0") => 0
    (parse-uint "-23") => nil
    (parse-uint "X") => nil))
