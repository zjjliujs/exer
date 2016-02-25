angular.module("constructorApp",[])
.controller("constructorCtrl",
["$scope", "$q",
function ($scope, $q){
    function okToGreet(name){
        var n = Math.round(Math.random() * 1000);
        return n % 2 == 0;
    }

    var totalTime = 5000;
    var checkInterval = 1000;
    var checkCnt = 0;
    var checkTimer;

    function asyncGreet (name) {
        var deferred = $q.defer();

        setTimeout (function(){
            if (okToGreet(name)){
                deferred.resolve ("Hello, " + name + "!");
            }else{
                deferred.reject("Sorry!I can't greet " + name);
            }
        }, totalTime);

        checkTimer = setInterval(function(){
            console.log (checkCnt);
            if (totalTime > checkCnt * checkInterval) {
                var remain = totalTime - checkCnt * checkInterval;
                deferred.notify("About to greet " + name + "in " + remain + "ms");
                ++checkCnt;
            }else{
                clearInterval(checkTimer);
                checkTimer = undefined;
            }
        }, checkInterval);

        return deferred.promise;
    }

    var promise = asyncGreet("陈胜");
    promise.then(function(greet){
        $scope.greet = greet;
        $scope.update = undefined;
    }, function(reason){
        $scope.failReason = reason;
    }, function(update){
        $scope.update = update;
    })
}]);
