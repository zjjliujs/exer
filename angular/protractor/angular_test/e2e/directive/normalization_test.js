'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('[Angular directive nomrlization] --', function() {

  beforeEach(function() {
    browser.get('angular/guide/directive/example1.html');
  });

  it('should show off bindings', function() {
    expect(element(by.css('div[ng-controller="Controller"] span[ng-bind]')).getText())
        .toBe('Max Karl Ernst Ludwig Planck (April 23, 1858 – October 4, 1947)');
  });
});
