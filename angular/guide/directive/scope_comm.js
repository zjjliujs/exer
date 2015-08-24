angular.module('myApp', [])
.directive('myTabs', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: {},
        controller: function($scope) {
            var panes = $scope.panes = [];

            $scope.select = function(pane) {
                angular.forEach(panes, function(pane) {
                    pane.selected = false;
                });
                pane.selected = true;
            };

            this.addPane = function(pane) {
                if (panes.length === 0) {
                    $scope.select(pane);
                }
                panes.push(pane);
            };
        },
        templateUrl: 'scope_comm_tabs.html'
    };
})
.directive('myPane', function() {
    return {
        require: '^myTabs',
        restrict: 'E',
        transclude: true,
        scope: {
            title: '@'
        },
        link: function(scope, element, attrs, tabsCtrl) {
            tabsCtrl.addPane(scope);
        },
        templateUrl: 'scope_comm_pane.html'
    };
});
