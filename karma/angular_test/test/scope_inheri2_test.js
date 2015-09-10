var app = angular.module('scopeInheri2App', []);

describe("[Scope Inheritance 2] -- ", function(){
  beforeEach(function(){
      module("scopeInheri2App");
  });

  it("Value inheritence", inject(function($rootScope){
    var parent = $rootScope;
    var child = parent.$new();

    parent.salutation = "Hello";
    expect(child.salutation).toEqual('Hello');

    child.salutation = "Welcome";
    expect(child.salutation).toEqual('Welcome');
    expect(parent.salutation).toEqual('Hello');
  }));
});
