app.config(['$routeProvider', '$locationProvider',
function($routeProvider, $locationProvider) {
    $routeProvider
    .when('/cluster-list', {
        templateUrl: 'parts/cluster-list.html',
        controller: 'clusterListCtrl',
        controllerAs: 'clusterList'
    })
    .when('/node-list', {
        templateUrl: 'parts/node-list.html',
        controller: 'nodeListCtrl',
        controllerAs: 'nodeList'
    })
    .when('/node-detail',{
        templateUrl: 'parts/node-detail.html',
        controller: 'nodeDetailCtrl',
        controllerAs: 'nodeDetail'
    })
    .when('/cluster-config',{
        templateUrl: 'parts/cluster-config.html',
        controller: 'clusterConfigCtrl',
        controllerAs: 'clusterConfig'
    })
    .otherwise({
        redirectTo: '/cluster-list'
    });

    $locationProvider.html5Mode(false);
}]);
