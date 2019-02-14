var fs = require("fs");
var data = fs.readFileSync('fs-read-sync.js');

console.log(data.toString());

console.log("===================================");
console.log("Program Ended");
console.log("===================================");
