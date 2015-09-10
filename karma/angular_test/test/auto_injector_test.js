describe("auto injector test", function(){
  it("$injector test", function(){
    var $injector = angular.injector();
    expect($injector.get('$injector')).toBe($injector);
    expect($injector.invoke(function($injector) {
      return $injector;
    })).toBe($injector);
  });
});
