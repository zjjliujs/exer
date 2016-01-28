app.controller('clusterConfigCtrl',
['$scope', 'monitorService', 'monitorResource',
function($scope, monitorService, monitorResource){
    var service = monitorService;

    var currentCluster = $scope.currentCluster = function (c){
        if (arguments.length > 0){
            service.pageStat.clusterConfig.currentCluster = c;
            //get the node list of the cluster
            $scope.nodes=monitorResource.query({name:c.nodeData});
            //console.log ($scope.nodes);
        }
        return service.pageStat.clusterConfig.currentCluster;
    };

    if (!currentCluster()){
        if ($scope.clusters.length > 0) {
            currentCluster($scope.clusters[0]);
        }
    }
}])
