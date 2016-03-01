myApp.config(["$stateProvider","$urlRouterProvider",
function($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise("contacts");

    $stateProvider.state('contacts', {
      url:'/contacts',
      template: '<h1>My Contacts</h1>'
    })
}]);
