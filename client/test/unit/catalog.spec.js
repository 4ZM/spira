'use strict';

describe('Catalog tests', function(){
  var $http, $httpBackend, $scope, ctrl;

  beforeEach(module('spira.catalog'));
  beforeEach(inject(function (_$http_, _$httpBackend_) {
    $http = _$http_;
    $httpBackend = _$httpBackend_;
  }));
  beforeEach(inject(function (_$rootScope_, _$controller_) {
    $scope = _$rootScope_.$new();
    ctrl = _$controller_('CatalogCtrl', {
      $scope : $scope
    });
  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });
});
