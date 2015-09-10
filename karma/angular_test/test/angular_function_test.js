describe("[Angular function tests] -- ", function() {
  it("angular.forEach function", function() {
    var values = {name: 'misko', gender: 'male'};
    var log = [];
    angular.forEach(values, function(value, key) {
      this.push(key + ': ' + value);
    }, log);
    expect(log).toEqual(['name: misko', 'gender: male']);
  });
});
