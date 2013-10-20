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

angular.module('spira.home', []);
angular.module('spira.catalog', []);
angular.module('spira.planner', []);

angular.module('spira', ['spira.home', 'spira.catalog', 'spira.planner']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'app/home/home.tpl.html', controller: 'HomeCtrl'});
    $routeProvider.when('/catalog', {templateUrl: 'app/catalog/catalog.tpl.html', controller: 'CatalogCtrl'});
    $routeProvider.when('/planner', {templateUrl: 'app/planner/planner.tpl.html', controller: 'PlannerCtrl'});
    $routeProvider.otherwise({redirectTo: '/home'});
  }]);
