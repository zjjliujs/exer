app.controller("mainController",
["$scope", "monitorService", "$location", "monitorResource",
function($scope, monitorService, $location, monitorResource){
    var service = $scope.service = monitorService;

    $scope.clusters = monitorResource.query();

    $scope.url = function () {
        return $location.url();
    };

    $scope.clusterTypes = service.getClusterTypes();
    //console.log ($scope.clusterTypes);

    $scope.showClusterCreator = function(s){
        if (arguments.length > 0) {
            service.pageStat.clusterCreator.show = s;
            service.pageStat.clusterCreator.clusterName = null;
            service.pageStat.clusterCreator.clusterType = $scope.clusterTypes[0];
        }
        return service.pageStat.clusterCreator.show;
    };

    $scope.showClusterEditor = function(cluster) {
        service.pageStat.clusterCreator.clusterName = cluster.name;
        service.pageStat.clusterCreator.clusterType = cluster.type;
        service.pageStat.clusterCreator.show = true;
    };

    $scope.showClusterRemover = function (cluster) {
        if (arguments.length > 0) {
            service.pageStat.clusterRemover.show = true;
            service.pageStat.clusterRemover.cluster = cluster;
        }
        return service.pageStat.clusterRemover.show;
    };

    $scope.showNodeCreator = function (cluster) {
        if (arguments.length > 0) {
            service.pageStat.nodeCreator.title= "增加节点";
            service.pageStat.nodeCreator.cluster = cluster;
            service.pageStat.nodeCreator.node = {};
            service.pageStat.nodeCreator.show = true;
        }
        return service.pageStat.nodeCreator.show;
    };

    $scope.showNodeEditor = function (cluster, node) {
        if (arguments.length > 0) {
            service.pageStat.nodeCreator.title= "编辑节点";
            service.pageStat.nodeCreator.cluster = cluster;
            service.pageStat.nodeCreator.node = node;
            service.pageStat.nodeCreator.show = true;
        }
        return service.pageStat.nodeCreator.show;
    };

    $scope.showNodeRemover = function (cluster, node) {
        if (arguments.length > 0) {
            service.pageStat.nodeRemover.show = true;
            service.pageStat.nodeRemover.cluster = cluster;
            service.pageStat.nodeRemover.node = node;
        }
        return service.pageStat.nodeRemover.show;
    };

}]);
