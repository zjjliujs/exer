app.controller('overviewCtrl',
["$scope","monitorService",
function($scope, monitorService){
    var service = monitorService;


    $scope.showClusterCreator = function () {
        service.dialogUrl('dialogs/cluster-creator.html');
        service.showDialog(true);
        //console.log (service.pageStat);
    }

    $scope.showNodeCreator = function (cluster) {
        service.dialogUrl('dialogs/node-creator.html');
        service.pageStat.nodeCreator.cluster = cluster;
        service.showDialog(true);
    }
}]);
