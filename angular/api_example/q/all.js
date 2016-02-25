angular.module("constructorApp",[])
.controller("constructorCtrl",
["$scope", "$q",
function ($scope, $q){
    function okToGreet(name){
        var n = Math.round(Math.random() * 1000);
        return n % 2 == 0;
    }

    function asyncGreet (name) {
        var deferred = $q.defer();

        setTimeout (function(){
            if (okToGreet(name)){
                console.log ("resolved " + name);
                deferred.resolve ("Hello, " + name + "!");
            }else{
                console.log ("rejected " + name);
                deferred.reject("Sorry!I can't greet " + name);
            }
        }, 3000 * Math.random());

        return deferred.promise;
    }

    var promise1 = asyncGreet("陈胜");
    var promise2 = asyncGreet("吴广");
    var promise3 = asyncGreet("李自成");

    var allProms = $q.all([promise1, promise2, promise3]);

    allProms.then(function(greets){
        console.log (greets);
        $scope.greets = greets;
    }, function(reason){
        console.log (reason);
        $scope.failReason = reason;
    });
}]);
