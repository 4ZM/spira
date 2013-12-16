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
    ['$scope', '$http', '$log', '$routeParams', '$location',
     function($scope, $http, $log, $routeParams, $location) {

       $scope.species = { val:{} };
       $scope.kinds = [];

       // Check if a property is modified.
       // without args, checks if any of the properties is modified.
       $scope.speciesModified = function speciesModified(field) {
         $log.info('Evaluating speciesModified');

         if (field === undefined) {
           var speciesFieldNames = ['name', 'family', 'genus', 'description'];
           for (var i = 0; i < speciesFieldNames.length; ++i) {
             if (speciesModified(speciesFieldNames[i]))
               return true;
           }
           return false;
         }

         return ($scope.species.val[field] === undefined) // new item
           || ($scope.species.val[field] !== $scope.species.orig[field]); //changed item
       };

       $scope.kindModified = function kindModified(kind, field) {
         if (field === undefined) {
           var kindFieldNames = ['name', 'description'];
           for (var i = 0; i < kindFieldNames.length; ++i) {
             if (kindModified(kind, kindFieldNames[i]))
               return true;
           }
           return false;
         }

         return (kind.orig === undefined) // new item
           || (kind.val[field] !== kind.orig[field]); // changed item
       };

       $log.info("PlantEditorCtrl");

       $log.info("route params: " + $routeParams);
       $log.info("route id: " + $routeParams.id);


       $scope.resetSpecies = function() {
         $log.info("reset species");
         $scope.species.val = angular.copy($scope.species.orig);
       };

       $scope.saveSpecies = function() {
         $log.info("save species");
         $log.info($scope.species);
       };

       $scope.deleteSpecies = function() {
         $log.info("delete species");

         if ($scope.species.orig === undefined) {
           $location.path('/catalog');
           return;
         }

         bootbox.dialog({
           message: 'Är det säkert att du vill ta bort arten <b>'
             + $scope.species.orig.name
             + '</b> och alla sorter knutna till den?',
           title: "Bekräfta borttagning",
           buttons: {
             cancel: {
               label: "Avbryt",
               className: "btn-default"
             },
             deleteSpecies: {
               label: '<i class="fa fa-trash-o fa-lg"></i>&nbsp; Ta bort',
               className: 'btn-danger',
               callback: function() {
                 $log.info('delete confirmed, calling rest api');
                 $http.delete("/api/species/" + $routeParams.id)
                   .success(function(response) {
                     $log.info('delete successfull');
                     $location.path('/catalog');
                   })
                   .error(function(response) {
                     throw new Error("Error requesting species: " +
                                     response.status);
                   });
               }
             }
           }
         });
       };

       $scope.addKind = function() {
         $log.info("add kind");
         $scope.kinds.push({ val:{} });
       };

       $scope.deleteKind = function(k) {
         $log.info("delete kind");
         var i = $scope.kinds.indexOf(k);
         if(i != -1) {
           $scope.kinds.splice(i, 1);
         }
       };

       $scope.resetKind = function(k) {
         $log.info("reset kind");
         k.val = angular.copy(k.orig);
       };

       $scope.saveKind = function(k) {
         $log.info("save kind");
       };

       if ($routeParams.id !== undefined) {
         $http.get("/api/species/" + $routeParams.id).
           success(function(response) {
             $scope.species.orig = response;
             $scope.species.val = angular.copy(response);

             $log.info("species req successfull");
             $log.info(response);

             $log.info("will now request kinds");
             $http.get("/api/species/" + $routeParams.id + '/kinds').
               success(function(response) {
                 $log.info("kind request completed");

                 for (var i = 0; i < response.length; ++i) {
                   var k = response[i];
                   $scope.kinds.push( { val:k, orig:angular.copy(k) } );
                 }

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
       }
     }]);

