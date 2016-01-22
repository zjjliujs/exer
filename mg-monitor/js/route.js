app.config(['$routeProvider', '$locationProvider',
function($routeProvider, $locationProvider) {
    $routeProvider
    .when('/overview', {
        templateUrl: 'parts/overview.html',
        controller: 'overviewCtrl',
        controllerAs: 'overview'
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
    .otherwise({
        redirectTo: '/overview'
    });

    $locationProvider.html5Mode(true);
}]);
