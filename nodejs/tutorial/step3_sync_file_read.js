var fs = require("fs");
var buf = fs.readFileSync (process.argv[2]);
var str = buf.toString("utf-8");
var sum = 0;
for(var i=0; i<str.length; ++i) {
	if (str[i] == '\n')
		++sum;
}
console.log(sum);
