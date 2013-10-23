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

(ns spira.dm.test-model-fixture
  (:require [clojure.test :refer :all]
            [spira.dm.test-model :refer :all]
            [spira.dm.test-util :as util]
            [spira.dm.garden :as garden]
            ;;[spira.dm.species :as species]
            )
  (:import [spira.dm.garden GardenRepo]))

;; Make sure we reset the repos before each test
(defn reset-repo-fixture [f]
  (setup-test-repos)
  (f))
(use-fixtures :each reset-repo-fixture)

(deftest garden-repo
  (testing "Test garden repo contains gardens")
  (is (< 0 (count (.list-gardens (garden/get-garden-repo))))))

(deftest garden-repo-add-get
  (testing "Test changing a garden")
  (let [repo (garden/get-garden-repo)
        initial-garden-count (count (.list-gardens repo))
        new-garden (garden/create-garden "Keukenhof")
        fetched-garden (.get-garden repo (.add-garden repo new-garden))]
    (is (= (+ initial-garden-count 1) (count (.list-gardens repo))))
    (is (= (.name new-garden) (.name fetched-garden)))
    ))

(deftest garden-repo-update
  (testing "Test adding gardens to repo")
  (let [repo (garden/get-garden-repo)
        garden-id (.add-garden repo (garden/create-garden "Keukenhof"))]
    (.update-garden repo garden-id (garden/create-garden "Drömparken"))
    (is (= (.name (.get-garden repo garden-id)) "Drömparken"))
    ))
