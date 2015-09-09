 //Create an object that contains the original function.
var originalObject = {
  minimum: 50,
  maximum: 100,
  checkNumericRange: function (value) {
    if (typeof value !== 'number')
      return false;
    else
      return value >= this.minimum && value <= this.maximum;
  }
}

// Check whether 10 is in the numeric range.
var result = originalObject.checkNumericRange(10);
console.log(result + " ");
// Output: false

// The range object supplies the range for the bound function.
var range = { minimum: 10, maximum: 20 };

// Create a new version of the checkNumericRange function that uses range.
var boundObjectWithRange = originalObject.checkNumericRange.bind(range);

// Check whether 10 is in the numeric range.
var result = boundObjectWithRange(10);
console.log (result);
