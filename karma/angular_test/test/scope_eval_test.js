var app = angular.module("scopeEvalApp", []);

describe("[Scope eval test] -- ", function(){
  beforeEach (module("scopeEvalApp"));

  it("scope eval test", inject(function($rootScope){
    var scope = $rootScope;
    scope.a = 1;
    scope.b = 2;

    expect(scope.$eval('a+b')).toEqual(3);
    expect(scope.$eval(function(scope){ return scope.a + scope.b; })).toEqual(3);
  }));

  it("scope eval test 2", function(){
    app.controller("testController", ["$scope", function($scope){
      var scope = $scope;
      scope.a = 1;
      scope.b = 2;

      expect(scope.$eval('a+b')).toEqual(3);
      expect(scope.$eval(function(scope){ return scope.a + scope.b; })).toEqual(3);
    }]);
  });
});
