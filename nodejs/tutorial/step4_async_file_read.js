var fs = require("fs");
var fname = process.argv[2];
var buf = fs.readFile(fname, function (err, data) {
	if (err != null) {
		console.log (err);
		return;
	}
	var str = data.toString("utf-8");
	var sum = 0;
	for(var i=0; i<str.length; ++i) {
		if (str[i] == '\n')
		++sum;
	}
	console.log(sum);
});
