angular.module('myApp', [])
.controller('Controller', ['$scope', function($scope) {
    $scope.customer = {
        name: 'Naomi',
        address: '1600 Amphitheatre'
    };
}])
.directive('myCustomer', function() {
    return {
        templateUrl: 'templateurl_test_part.html'
    };
});
