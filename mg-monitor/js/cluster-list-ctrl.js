app.controller('clusterListCtrl',
["$scope","monitorService","$location",
function($scope, monitorService, $location){
    var service = monitorService;

    $scope.gotoNodeList = function (cluster) {
        //console.log (cluster);
        service.pageStat.nodeList.cluster = cluster;
        $location.url("/node-list");
        console.log($location.url());
    };
}]);
