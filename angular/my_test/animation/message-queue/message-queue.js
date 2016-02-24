app.controller("messageQueueCtrl",
["$scope","$interval",
function($scope, $interval){
    var messages= [];
    $scope.messages = messages;

    var msgCnt = 0;

    $scope.sendMessage = function () {
        var msg = "消息" + msgCnt++;
        messages.unshift(msg);
    }

    var checkTimer;
    $scope.$watchCollection('messages', function(nv,ov,scope){
        console.log ("in interval");
        if (angular.isDefined(checkTimer)) {
            //已经有interval在运行
            return;
        }

        if (nv.length != 0) {
            checkTimer = $interval(function(){
                messages.pop();
                if (messages.length == 0) {
                    $interval.cancel(checkTimer);
                    checkTimer = undefined;
                }
            }, 3000);
        }
    })
}])
