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

angular.module('spira.catalog.create-plant-desc')
  .controller(
    'CreatePlantDescCtrl',
    ['$scope', '$http', '$log', function($scope, $http, $log) {

      $scope.plant = {};

      // Handler for the create plant desc. button
      $scope.addPlantDesc = function() {

        // Call the create api
        $http.post("/api/plantdesc", $scope.plant).
          success(function(response) {

            // Get the id of the newly created plant desc
            $scope.data = response.data;
          }).
          error(function(response) {
            throw new Error("Error creating plant description: " +
                            response.status);
          });
      };
    }]);

