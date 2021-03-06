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

(ns spira.core.util)

(defn uuid []
  "Create a GUID string."
  (str (java.util.UUID/randomUUID)))

(defn parse-uint [s]
  "a2uint (nil if not a number)"
  (if (re-find #"^\d+$" s)
    (read-string s)))

(defn find-first [f coll]
  "Get first item in a seq matching a predicate."
  (first (filter f coll)))
