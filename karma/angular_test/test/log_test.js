var myMod = angular.module('logApp', []);

// Controller definition ...
myMod.controller('mockTestController', ['$log', function($log) {
  $log.info(this.name);
}]);

describe('[log test] -- ', function() {

  // You need to load modules that you want to test,
  // it loads only the "ng" module by default.
  beforeEach(module('logApp'));

  it('should write the bound name to the log', inject(function($controller, $log) {
    var ctrl = $controller('mockTestController', { /* no locals */ }, { name: 'Clark Kent' });
    expect(ctrl.name).toEqual('Clark Kent');
    expect($log.info.logs[0]).toEqual(['Clark Kent']);
  }));

});
