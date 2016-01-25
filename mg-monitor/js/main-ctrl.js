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
        }
        return service.pageStat.clusterCreator.show;
    };

    $scope.showClusterRemover = function (cluster) {
        if (arguments.length > 0) {
            service.pageStat.clusterRemover.show = true;
            service.pageStat.clusterRemover.cluster = cluster;
        }
        return service.pageStat.clusterRemover.show;
    };

    $scope.showNodeCreator = function (s) {
        if (arguments.length > 0) {
            service.pageStat.nodeCreator.show = true;
        }
        return service.pageStat.nodeCreator.show;
    };

    $scope.showNodeRemover = function (node) {
        if (arguments.length > 0) {
            service.pageStat.nodeRemover.show = true;
            service.pageStat.nodeRemover.node = node;
        }
        return service.pageStat.nodeRemover.show;
    };

}]);
