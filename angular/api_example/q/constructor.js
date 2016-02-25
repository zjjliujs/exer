angular.module("constructorApp",[])
.controller("constructorCtrl",
["$scope", "$q",
function ($scope, $q){
    function okToGreet(name){
        var n = Math.round(Math.random() * 1000);
        return n % 2 == 0;
    }

    function asyncGreet (name) {
        return $q(function (resolve, reject){
            setTimeout (function(){
                if (okToGreet(name)){
                    resolve ("Hello, " + name + "!");
                }else{
                    reject("Sorry!I can't greet " + name);
                }
            }, 1000);
        });
    }

    var promise = asyncGreet("陈胜");
    promise.then(function(greet){
        $scope.greet = greet;
    }, function(reason){
        $scope.failReason = reason;
    })
}]);
