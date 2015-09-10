var app =  angular.module('scopeMockApp', []);

app.controller('MyController', ['$scope', function($scope) {
  $scope.username = 'World';

  $scope.sayHello = function() {
    $scope.greeting = 'Hello ' + $scope.username + '!';
  };
}]);

describe("[Scope mock test] --", function(){
  var scopeMock = {};
  var MyController;

  beforeEach(module("scopeMockApp"));

  beforeEach(inject(function($controller){
    MyController = $controller("MyController", {$scope:scopeMock});
  }));

  it('should say hello', function() {
    // Assert that username is pre-filled
    expect(scopeMock.username).toEqual('World');

    // Assert that we read new username and greet
    scopeMock.username = 'angular';
    scopeMock.sayHello();
    expect(scopeMock.greeting).toEqual('Hello angular!');
  });
});
