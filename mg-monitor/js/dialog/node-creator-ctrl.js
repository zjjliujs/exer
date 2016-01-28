app.controller("nodeCreatorController",
["$scope", "monitorService",
function($scope, monitorService){
    var service = monitorService;

    $scope.dialogTitle = function (title) {
        if (arguments.length > 0) {
            service.pageStat.nodeCreator.title = title;
        }
        return service.pageStat.nodeCreator.title;
    }

    $scope.cluster = function (c) {
        //console.log (c);
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.cluster = c;
        }
        return service.pageStat.nodeCreator.cluster;
    };

    $scope.nodeHost = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.node.host = c;
        }
        return service.pageStat.nodeCreator.node.host;
    };

    $scope.nodeIp = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.node.ip = c;
        }
        return service.pageStat.nodeCreator.node.ip;
    };

    $scope.hideDialog = function () {
        service.pageStat.nodeCreator.show = false;
    };

    $scope.saveNode = function () {
        $scope.hideDialog();
    };

}]);
