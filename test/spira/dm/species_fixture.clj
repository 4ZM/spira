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

(ns spira.dm.species-fixture
  (:require [clojure.test :refer :all]
            [spira.dm.test-util :as util]
            [spira.dm.species :refer :all])
  (:import [spira.dm.species PlantSpecies]))

(def carrot-early-nantes (util/create-test-plant "Carrot" "Early Nantes"))
(def carrot-amsterdam (util/create-test-plant "Carrot" "Amsterdam"))
(def corn-ashworth (util/create-test-plant "Corn" "Ashworth"))

(deftest test-id-func
  (testing "PlantSpecies id func."
    (is (= (id carrot-early-nantes) (id carrot-early-nantes)))
    (is (not (= (id carrot-early-nantes) (id carrot-amsterdam))))
    (is (not (= (id carrot-early-nantes) (id corn-ashworth))))
    ))





