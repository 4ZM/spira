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

    it('should return all species', inject(function() {
        // Create test here
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});
