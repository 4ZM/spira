'use strict';


// Declare app level module which depends on filters, and services
angular.module('spira', ['spira.filters', 'spira.services', 'spira.directives', 'spira.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'homeCtrl'});
    $routeProvider.when('/catalog', {templateUrl: 'partials/catalog.html', controller: 'catalogCtrl'});
    $routeProvider.when('/planner', {templateUrl: 'partials/planner.html', controller: 'plannerCtrl'});
    $routeProvider.otherwise({redirectTo: '/home'});
  }]);
