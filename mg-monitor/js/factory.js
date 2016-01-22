app.factory('monitorResource',
['$resource',
function($resource){
    return $resource('data/:name.json', {name:"clusters"}); 
}]);
