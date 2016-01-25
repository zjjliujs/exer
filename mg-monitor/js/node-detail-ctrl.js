app.controller('nodeDetailCtrl',
["$scope", "monitorService", "monitorResource",
function($scope, monitorService, monitorResource){
    var service = monitorService;

    var cluster = $scope.cluster = function(c) {
        if (arguments.length > 0) {
            service.pageStat.nodeDetail.cluster = c;
        }
        return service.pageStat.nodeDetail.cluster;
    };

    //设置默认的集群
    //console.log ($scope.clusters);
    if (!cluster() && $scope.clusters && $scope.clusters.length > 0) {
        //console.log ($scope.clusters[0]);
        cluster($scope.clusters[0]);
    };
    //console.log ($scope.cluster());

    $scope.clusterChanged = function() {
        if (cluster()){
            var name = cluster().nodeData;
            $scope.nodes=monitorResource.query({name:name}, function(){
                if ($scope.nodes && $scope.nodes.length > 0){
                    $scope.node($scope.nodes[0]);
                }
            });
            //console.log($scope.nodes);
        }
    };

    $scope.node = function (n){
        if (arguments.length > 0){
            service.pageStat.nodeDetail.node = n;
        }
        return service.pageStat.nodeDetail.node;
    };

    $scope.clusterChanged();

    $scope.nodeChanged = function() {
        //to get detail information
    };

}]);
