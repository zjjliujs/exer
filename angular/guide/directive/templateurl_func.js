angular.module('myApp', [])
.controller('Controller', ['$scope', function($scope) {
    $scope.customer = {
        name: 'Naomi',
        address: '1600 Amphitheatre'
    };
}])
.directive('myCustomer', function() {
    return {
        templateUrl: function(elem, attr){
            console.log(elem);
            console.log(attr);
            return 'templateurl_func_'+attr.type+'.html';
        }
    };
});
