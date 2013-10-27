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

(ns spira.dm.garden-fixture
  (:require [clojure.test :refer :all]
            [spira.dm.garden :refer :all]))

;; Make sure we reset the garden repo before each test
(defn reset-repo-fixture [f]
  (set-garden-repo nil)
  (f))
(use-fixtures :each reset-repo-fixture)

;; Tests

(deftest test-names
  (testing "Testing the garden identity function")
  (let [babylon (create-garden "babylon")
        keukenhof (create-garden "Keukenhof")]
    (is (= (.name babylon) (.name babylon)))
    (is (not (= (.name babylon) (.name keukenhof)))))
    )

(deftest test-repo-locator
  (testing "Test the garden locator")
  ;; Must assign before first req
  (is (thrown? AssertionError (get-garden-repo)))
  ;; Then it works
  (set-garden-repo :repo)
  (is (= (get-garden-repo) :repo))
  ;; But can't be assigned again directly
  (is (thrown? AssertionError (set-garden-repo :repo)))
  )
