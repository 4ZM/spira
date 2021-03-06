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

(ns spira.rest-adapter.garden-service-fixture
  (:require [midje.sweet :refer :all]
            [spira.core.test-util :refer :all]
            [spira.dm.garden :as garden]
            [spira.rest-adapter.util :refer :all]
            [spira.rest-adapter.garden-service :refer :all]
            ))

(facts "about garden list requests"
  (fact "garden list request requests gardens"
    (req-garden-list :repo) => (response :ok '())
    (provided (garden/list-gardens :repo) => '())))

(facts "about garden requests"
  (fact "garden request of non existent garden gives :bad-req"
    (req-garden :repo 23) => (response :bad-req)
    (provided (garden/get-garden :repo 23) => nil))
  (fact "garden request of existing garden returns it"
    (req-garden :repo 23) => (response :ok :garden)
    (provided (garden/get-garden :repo 23) => :garden)))

(facts "about create garden requests"
  (fact "create request creates a garden"
    (create-garden :repo {:name "torture"}) => (response :created 23)
    (provided (garden/add-garden :repo (mapped-eq? :name "torture")) => 23)))

(facts "about update garden requests"
  (fact "update request updates a garden"
    (fact "update with invalid id gives :bad-req"
      (update-garden :repo 23 {}) => (response :bad-req)
      (provided (garden/update-garden :repo 23 anything) => false))))

(facts "about delete garden requests"
  (fact "delete request deletes a garden"
    (delete-garden :repo 23) => (response :ok)
    (provided (garden/delete-garden :repo 23) => true))
  (fact "delete request with invalid id"
    (delete-garden :repo 23) => (response :bad-req)
    (provided (garden/delete-garden :repo 23) => false)))
