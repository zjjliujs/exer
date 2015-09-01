//Define the original function with four parameters.
var displayArgs = function (val1, val2, val3, val4) {
  console.log(val1 + " " + val2 + " " + val3 + " " + val4);
}

var emptyObject = {};

// Create a new function that uses the 12 and "a" parameters
// as the first and second parameters.
var displayArgs2 = displayArgs.bind(emptyObject, 12, "a");

// Call the new function. The "b" and "c" parameters are used
// as the third and fourth parameters.
displayArgs2("b", "c");
// Output: 12 a b c 
