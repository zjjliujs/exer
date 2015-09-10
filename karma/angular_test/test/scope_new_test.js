var myApp = angular.module('scopeNewApp', []);

myApp.controller('MyController', function($scope) {
  $scope.spices = [{"name":"pasilla", "spiciness":"mild"},
                   {"name":"jalapeno", "spiciness":"hot hot hot!"},
                   {"name":"habanero", "spiciness":"LAVA HOT!!"}];
  $scope.spice = "habanero";
});

describe('my Controller test', function() {
  var test_scope;

  beforeEach(module('scopeNewApp'));

  beforeEach(inject(function($rootScope, $controller) {
    test_scope = $rootScope.$new();
    $controller('MyController', {$scope: test_scope});
  }));

  it('should create "spices" model with 3 spices', function() {
    expect(test_scope.spices.length).toBe(3);
  });

  it('should set the default value of spice', function() {
    expect(test_scope.spice).toBe('habanero');
  });
});
