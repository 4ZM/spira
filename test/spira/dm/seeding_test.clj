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

(ns spira.dm.seeding-test
  (:require [clojure.test :refer :all]
            [spira.dm.test-util :as util]
            [spira.dm.garden :as garden]
            [spira.dm.species :as species])
  (:import [spira.dm.species PlantSpecies])
  (:use spira.dm.seeding))
 
(def carrot (util/create-test-plant "Carrot" "Early Nantes"))
(def corn (util/create-test-plant "Corn" "Ashworth"))

(deftest seeding-creation
  (testing "Test seeding creation"
    (is (not (= (create-seeding carrot) (create-seeding carrot))))
    (is (not (= (create-seeding carrot) (create-seeding corn))))))
