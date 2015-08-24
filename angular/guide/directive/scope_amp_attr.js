angular.module('myApp', [])
.controller('Controller', ['$scope', '$timeout', function($scope, $timeout) {
    $scope.name = 'Tobias';
    $scope.message = '';
    $scope.hideDialog = function (msg) {
        $scope.message = msg;
        console.log(typeof(msg));
        $scope.dialogIsHidden = true;
        $timeout(function () {
            $scope.message = '';
            $scope.dialogIsHidden = false;
        }, 2000);
    };
}])
.directive('myDialog', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: {
            'close': '&onClose'
        },
        templateUrl: 'scope_amp_attr_dialog.html'
    };
});
