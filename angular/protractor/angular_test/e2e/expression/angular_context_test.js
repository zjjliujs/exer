'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('[Angular context test] --', function() {

  beforeEach(function() {
    browser.get('angular/guide/expression/angular_context.html');
  });



  it('should calculate expression in binding', function() {
    if (browser.params.browser == 'safari') {
      // Safari can't handle dialogs.
      return;
    }
    element(by.css('[ng-click="greet()"]')).click();
    var alertDialog = browser.switchTo().alert();
    expect(alertDialog.getText()).toEqual('Hello World');
    alertDialog.accept();
  });
});
