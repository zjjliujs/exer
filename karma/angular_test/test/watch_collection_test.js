var app = angular.module("watchCollectionApp", []);

describe("[Watch collection Test] --", function(){
  
  beforeEach (module("watchCollectionApp"));

  it("Test watch collection", inject(function($rootScope){
    var $scope = $rootScope.$new();
    $scope.names = ['igor', 'matias', 'misko', 'james'];
    $scope.dataCount = 4;

    $scope.$watchCollection('names', function(newNames, oldNames) {
      $scope.dataCount = newNames.length;
    });

    expect($scope.dataCount).toEqual(4);
    $scope.$digest();

    //still at 4 ... no changes
    expect($scope.dataCount).toEqual(4);

    $scope.names.pop();
    $scope.$digest();

    //now there's been a change
    expect($scope.dataCount).toEqual(3);
  }));
});
