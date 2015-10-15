var total = [0, 1, 2, 3, 4].reduce(function(previousValue, currentValue, index, array) {
    return previousValue + currentValue;
});

console.log (total);

total = [0, 1, 2, 3, 4].reduce(function(previousValue, currentValue, index, array) {
    return previousValue + currentValue;
}, 10);
console.log (total);

var flattened = [[0, 1], [2, 3], [4, 5]].reduce(function(a, b) {
      return a.concat(b);
}, []);
// flattened is [0, 1, 2, 3, 4, 5]
console.log (flattened);
