app.controller("mainController",
["$scope", "monitorService", "$location", "monitorResource",
function($scope, monitorService, $location, monitorResource){
    var service = monitorService;

    $scope.clusters = monitorResource.query();

    $scope.url = function () {
        return $location.url();
    };

    $scope.showDialog = function(){
        return service.showDialog();
    }

    $scope.dialogUrl = function () {
        return service.dialogUrl();
    }

    $scope.clusterName = function (n) {
        if (arguments.length > 0) {
            service.pageStat.clusterCreator.clusterName = n;
        }
        return service.pageStat.clusterCreator.clusterName;
    }

    $scope.clusterType = function (t) {
        if (arguments.length > 0) {
            service.pageStat.clusterCreator.clusterType = t;
        }
        //console.log (service.pageStat.clusterCreator.clusterType);
        return service.pageStat.clusterCreator.clusterType;
    }

    $scope.clusterTypes = service.getClusterTypes();
    //console.log ($scope.clusterTypes);

    $scope.hideDialog = function () {
        service.hideDialog();
    }

    $scope.saveCluster = function () {
        $scope.hideDialog();
    }

    $scope.cluster = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.cluster = c;
        }
        return service.pageStat.nodeCreator.cluster;
    }

    $scope.hostName = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.hostName = c;
        }
        return service.pageStat.nodeCreator.hostName;
    }

    $scope.ip = function (c) {
        if (arguments.lenght > 0) {
            service.pageStat.nodeCreator.ip = c;
        }
        return service.pageStat.nodeCreator.ip;
    }
}]);
