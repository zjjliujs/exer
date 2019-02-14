var fs = require("fs");

fs.readFile('fs-read-async.js', function (err, data) {
   if (err) { 
	return console.error(err);
   }

   console.log(data.toString());
});

console.log("===================================");
console.log("Program Ended");
console.log("===================================");
