angular.module('docsSimpleDirective', [])
.controller('Controller', ['$scope', function($scope) {
    $scope.customer = {
        name: 'Naomi',
        address: '1600 Amphitheatre'
    };
}])
.directive('myCustomer', function() {
    console.log("Directive factory function enter!")
    return {
        template: 'Name: {{customer.name}} Address: {{customer.address}}'
    };
});
