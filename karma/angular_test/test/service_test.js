angular.
module('myServiceApp', []).
controller('MyController', ['$scope','notify', function ($scope, notify) {
  $scope.callNotify = function(msg) {
    notify(msg);
  };
}]).
factory('notify', ['$window', function(win) {
  var msgs = [];
  return function(msg) {
    msgs.push(msg);
    if (msgs.length == 3) {
      win.alert(msgs.join("\n"));
      msgs = [];
    }
  };
}]);

describe("[Service test] --", function(){
  var mock, notify;

  beforeEach(function() {
    mock = {alert: jasmine.createSpy()};

    module("myServiceApp", function($provide) {
      $provide.value('$window', mock);
    });

    inject(function($injector) {
      notify = $injector.get('notify');
    });
  });

  it('should not alert first two notifications', function() {
    notify('one');
    notify('two');

    expect(mock.alert).not.toHaveBeenCalled();
  });

  it('should alert all after third notification', function() {
    notify('one');
    notify('two');
    notify('three');

    expect(mock.alert).toHaveBeenCalledWith("one\ntwo\nthree");
  });

  it('should clear messages after alert', function() {
    notify('one');
    notify('two');
    notify('third');
    notify('more');
    notify('two');
    notify('third');

    expect(mock.alert.callCount).toEqual(2);
    expect(mock.alert.mostRecentCall.args).toEqual(["more\ntwo\nthird"]);
  });
});
