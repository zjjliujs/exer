app.controller('nodeListCtrl',
["$scope", "monitorService", "monitorResource",
function($scope, monitorService, monitorResource){
    var service = monitorService;

    $scope.cluster = function(c) {
        if (arguments.length > 0) {
            service.pageStat.nodeList.cluster = c;
        }
        //console.log(service.pageStat.nodeList.cluster);
        return service.pageStat.nodeList.cluster;
    };

    $scope.clusterChanged = function() {
        console.log (service.pageStat.nodeList.cluster);
        var name = "all";
        if (service.pageStat.nodeList.cluster){
            name = service.pageStat.nodeList.cluster.nodeData;
        }
        $scope.nodes=monitorResource.query({name:name});
        console.log($scope.nodes);
    };

    $scope.clusterChanged();
    //console.log($scope.nodes);

    $scope.orderTypes = [
        {name:"ip"},
        {name:"cpu占用"},
        {name:"内存占用"},
        {name:"硬盘占用"}
    ];

    $scope.orderType = function (t){
        if(arguments.length > 0) {
            service.pageStat.nodeList.order.type = t;
        }
        return service.pageStat.nodeList.order.type;
    };

    $scope.orderUp = function () {
        return service.pageStat.nodeList.order.asc;
    };

    $scope.orderDown = function () {
        return !service.pageStat.nodeList.order.asc;
    };

    $scope.toggleOrder = function (){
        service.pageStat.nodeList.order.asc = !service.pageStat.nodeList.order.asc;
    };

    $scope.filter = function (f) {
        if (arguments.length > 0) {
            service.pageStat.nodeList.filter = f;
        }
        return service.pageStat.nodeList.filter;
    };
}]);
