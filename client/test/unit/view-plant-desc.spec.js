'use strict';

describe('View plant desc tests', function(){
  var $http, $httpBackend, $scope, ctrl;

  beforeEach(module('spira.catalog.view-plant-desc'));
  beforeEach(inject(function (_$http_, _$httpBackend_) {
    $http = _$http_;
    $httpBackend = _$httpBackend_;
  }));
  beforeEach(inject(function (_$rootScope_, _$controller_) {
    $scope = _$rootScope_.$new();
    ctrl = _$controller_('ViewPlantDescCtrl', {
      $scope : $scope
    });
  }));

//  it('should request a plant desc creation', inject(function() {
//  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });
});
