'use strict';

describe('spira', function() {

  beforeEach(function() {
    browser().navigateTo('/index.html');
  });

  it('should automatically redirect to /home when location hash/fragment is empty', function() {
    expect(browser().location().url()).toBe("/home");
  });

  describe('home', function() {

    beforeEach(function() {
      browser().navigateTo('#/home');
    });

    it('should render home when user navigates to /home', function() {
        expect(element('[ng-view] h1:first').text()).toMatch('Hem');
    });

  });

});
