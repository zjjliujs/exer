function callMe(arg1, arg2){
  var s = "";

  s += "this value: " + this + "\n";
  for (i in callMe.arguments) {
    s += "arguments: " + callMe.arguments[i] + "\n";
  }
  return s;
}

console.log("Original function:");
console.log(callMe(1, 2));

console.log("Function called with call:");
console.log(callMe.call(3, 4, 5));

