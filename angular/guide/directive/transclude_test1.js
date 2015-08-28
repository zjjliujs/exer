angular.module('myApp', [])
.controller('Controller', ['$scope', function($scope) {
  $scope.name = 'Tobias';
}])
.directive('myDialog', function() {
  return {
    restrict: 'E',
    transclude: true,
    templateUrl: 'transclude_test1_dialog.html'
  };
});
