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

// Add controller to module
angular.module('spira.catalog')
  .controller('CatalogCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.plantDesc = {};
    $scope.speciesVisible = {};

    $scope.queryPlantDescs = function() {
      $http.get('/api/plantdesc')
        .success(function (data, status, headers, config) {
          $scope.plantDesc = data;
        })
        .error(function (data, status, headers, config) {
          throw new Error("Can't get fubar");
        });
    };

    $scope.queryPlantDescs();
  }]);

