'use strict';

describe('Create plant desc tests', function(){
  var $http, $httpBackend, $scope, ctrl;

  beforeEach(module('spira.catalog.create-plant-desc'));
  beforeEach(inject(function (_$http_, _$httpBackend_) {
    $http = _$http_;
    $httpBackend = _$httpBackend_;
  }));
  beforeEach(inject(function (_$rootScope_, _$controller_) {
    $scope = _$rootScope_.$new();
    ctrl = _$controller_('CreatePlantDescCtrl', {
      $scope : $scope
    });
  }));

  it('should request a plant desc creation', inject(function() {
    $httpBackend
      .expectPOST('/api/plantdesc',
                  '{"family":"f",' +
                  '"species":"s",' +
                  '"genus":"g",' +
                  '"kind":"k"}')
      .respond(201, '[{"id":1,"kind":"k"}]');

    // Fill out form
    $scope.plant.family = "f";
    $scope.plant.species = "s";
    $scope.plant.genus = "g";
    $scope.plant.kind = "k";

    // Klick submit
    $scope.addPlantDesc();

    // TODO Test progress indicator starts ...

    $httpBackend.flush();

    // TODO Test progress indicator stops ...
  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });
});
