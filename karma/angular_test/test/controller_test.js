var myMod = angular.module('controllerTestApp', [])
.value('mode', 'app')
.value('version', 'v1.0.1');

myMod.controller('DoubleController', ['$scope', function($scope) {
  $scope.message = 'hello,world!';
  $scope.double = function (n) {
    return 2 * n;
  };
}]);

describe('MyApp', function() {
  var scope, ctrl;
  // You need to load modules that you want to test,
  // it loads only the "ng" module by default.
  beforeEach(module('controllerTestApp'));

  beforeEach(inject(function($controller) {
     scope = {};
     ctrl = $controller('DoubleController', {$scope:scope});
   }));

  it('Message should be hello,world!', function() {
    expect(scope.message).toEqual('hello,world!');
  });

  it('Double function test', function() {
    expect(scope.double(1)).toEqual(2);
  });
});
