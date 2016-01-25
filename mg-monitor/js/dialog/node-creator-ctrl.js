app.controller("nodeCreatorController",
["$scope", "monitorService",
function($scope, monitorService){
    var service = monitorService;

    $scope.cluster = function (c) {
        //console.log (c);
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.cluster = c;
        }
        return service.pageStat.nodeCreator.cluster;
    };

    $scope.hostName = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.hostName = c;
        }
        return service.pageStat.nodeCreator.hostName;
    };

    $scope.ip = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.ip = c;
        }
        return service.pageStat.nodeCreator.ip;
    };

    $scope.hideDialog = function () {
        service.pageStat.nodeCreator.show = false;
    };

    $scope.saveNode = function () {
        $scope.hideDialog();
    };

}]);
