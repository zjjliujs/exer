'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('[Simple Expression] --', function() {

    beforeEach(function() {
      browser.get('angular/guide/expression/simple.html');
    });

    it('should calculate expression in binding', function() {
      expect(element(by.binding('1+2')).getText()).toEqual('1+2=3');
    });
});
