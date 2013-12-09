// Copyright (C) 2013 Anders Sundman <anders@4zm.org>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

'use strict';

angular.module('spira.catalog.plant-editor')
  .controller(
    'PlantEditorCtrl',
    ['$scope', '$http', '$log', '$routeParams',
     function($scope, $http, $log, $routeParams) {

       $scope.species = {};
       $scope.kinds = {};

       $log.info("PlantEditorCtrl");

       $log.info("route params: " + $routeParams);
       $log.info("route id: " + $routeParams.id);


       $scope.saveSpecies = function() {
         $log.info("save species");
       };

       $scope.deleteSpecies = function() {
         $log.info("delete species");
       };

       $scope.addKind = function() {
         $log.info("add kind");
         $scope.kinds.push({});
       };

       $scope.deleteKind = function(k) {
         $log.info("delete kind");
         var i = $scope.kinds.indexOf(k);
         if(i != -1) {
           $scope.kinds.splice(i, 1);
         }
       };

       $scope.saveKind = function(k) {
         $log.info("save kind");
       };

       $http.get("/api/species/" + $routeParams.id).
         success(function(response) {
           $scope.species = response;

           $log.info("will now request kinds");
           $http.get("/api/species/" + $routeParams.id + '/kinds').
             success(function(response) {
               $log.info("kind request completed");
               $scope.kinds = response;
               $log.info($scope.kinds);
             }).
             error(function(response) {
               throw new Error("Error requesting species kinds: " +
                               response.status);
             });
         }).
         error(function(response) {
           throw new Error("Error requesting species: " +
                           response.status);
         });
     }]);

