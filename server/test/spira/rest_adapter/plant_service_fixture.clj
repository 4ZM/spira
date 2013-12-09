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

(ns spira.rest-adapter.plant-service-fixture
  (:require [midje.sweet :refer :all]
            [spira.core.test-util :refer :all]
            [spira.dm.plant :as p]
            [spira.rest-adapter.util :refer :all]
            [spira.rest-adapter.plant-service :refer :all]
            ))

(facts "about plant description list requests"
  (fact "plant description list request requests plant descriptions"
    (req-species :repo) => (response :ok '())
    (provided (p/species :repo) => '())))

(facts "about plant description requests"
  (fact "plant description request of non existent description gives :bad-req"
    (req-species :repo 23) => (response :bad-req)
    (provided (p/species :repo 23) => nil))
  (fact "plant description request of existing description returns it"
    (req-species :repo 23) => (response :ok :plant-desc)
    (provided (p/species :repo 23) => :plant-desc)))

(facts "about create plant description requests"
  (fact "create request creates a description"
    (create-species :repo {:name "sn" :family "f" :genus "g"})
    => (response :created 23)
    (provided (p/add-species :repo (mapped-eq? :name "sn")) => 23)))

;; TODO write test for update when semantics become clear

(facts "about delete plant description requests"
  (fact "delete request deletes a description"
    (delete-species :repo 23) => (response :ok)
    (provided (p/delete-species :repo 23) => true))
  (fact "delete request with invalid id"
    (delete-species :repo 23) => (response :bad-req)
    (provided (p/delete-species :repo 23) => false)))
