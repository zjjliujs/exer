app.controller("clusterRemoverController",
["$scope", "monitorService",
function($scope, monitorService){
    var service = monitorService;

    $scope.cluster = function () {
        return service.pageStat.clusterRemover.cluster;
    };

    $scope.hideDialog = function () {
        service.pageStat.clusterRemover.show = false;
    };

    $scope.deleteCluster = function () {
        $scope.hideDialog();
    };

}])
