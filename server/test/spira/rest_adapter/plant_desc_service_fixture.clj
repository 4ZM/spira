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

(ns spira.rest-adapter.plant-desc-service-fixture
  (:require [midje.sweet :refer :all]
            [spira.core.test-util :refer :all]
            [spira.dm.plant-desc :as pd]
            [spira.rest-adapter.util :refer :all]
            [spira.rest-adapter.plant-desc-service :refer :all]
            ))

(facts "about plant description list requests"
  (fact "plant description list request requests plant descriptions"
    (req-plant-desc-list :repo) => (response :ok '())
    (provided (pd/list-descriptions :repo) => '())))

(facts "about plant description requests"
  (fact "plant description request of non existent description gives :bad-req"
    (req-plant-desc :repo 23) => (response :bad-req)
    (provided (pd/get-plant-desc :repo 23) => nil))
  (fact "plant description request of existing description returns it"
    (req-plant-desc :repo 23) => (response :ok :plant-desc)
    (provided (pd/get-plant-desc :repo 23) => :plant-desc)))

(facts "about create plant description requests"
  (fact "create request creates a description"
    (create-plant-desc
     :repo {:family "f" :genus "g" :species "s" :kind "k"})
    => (response :created 23)
    (provided (pd/add-plant-desc :repo (mapped-eq? #(:kind (:name %)) "k"))
              => 23)))

(facts "about update plant description requests"
  (fact "update request updates a description"
    (fact "update with invalid id gives :bad-req"
      (update-plant-desc :repo 23 {}) => (response :bad-req)
      (provided (pd/update-plant-desc :repo 23 anything) => false))))

(facts "about delete plant description requests"
  (fact "delete request deletes a description"
    (delete-plant-desc :repo 23) => (response :ok)
    (provided (pd/delete-plant-desc :repo 23) => true))
  (fact "delete request with invalid id"
    (delete-plant-desc :repo 23) => (response :bad-req)
    (provided (pd/delete-plant-desc :repo 23) => false)))
