app.controller("nodeRemoverController",
["$scope", "monitorService",
function($scope, monitorService){
    var service = monitorService;

    $scope.node = function () {
        //console.log (service.pageStat.nodeRemover.node);
        return service.pageStat.nodeRemover.node;
    };

    $scope.cluster = function () {
        return service.pageStat.nodeList.cluster;
    };

    $scope.hideDialog = function () {
        service.pageStat.nodeRemover.show = false;
    };

    $scope.deleteCluster = function () {
        $scope.hideDialog();
    };

}])
