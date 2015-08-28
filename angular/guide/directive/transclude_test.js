angular.module('myApp', [])
.controller('Controller', ['$scope', function($scope) {
    $scope.name = 'Tobias';
}])
.directive('myDialog', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: {},
        templateUrl: 'transclude_test_dialog.html',
        link: function (scope, element) {
            scope.name = 'Jeff';
        }
    };
});
