app.controller("clusterCreatorController",
["$scope", "monitorService",
function($scope, monitorService){
    var service = monitorService;

    $scope.clusterName = function (n) {
        if (arguments.length > 0) {
            service.pageStat.clusterCreator.clusterName = n;
        }
        return service.pageStat.clusterCreator.clusterName;
    };

    $scope.clusterType = function (t) {
        if (arguments.length > 0) {
            service.pageStat.clusterCreator.clusterType = t;
        }
        //console.log (service.pageStat.clusterCreator.clusterType);
        return service.pageStat.clusterCreator.clusterType;
    };

    $scope.hideDialog = function () {
        service.pageStat.clusterCreator.show = false;
    };

    $scope.saveCluster = function () {
        $scope.hideDialog();
    };

}]);
