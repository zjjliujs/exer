var http = require ("http");
var bl = require ("bl");

var url = process.argv[2];
var req = http.get (url , function (response) {
  response.pipe (bl (function(err, data) {
    console.log (data.length);
    console.log (data.toString("utf-8"));
  }));
})

req.on ("error", function (error) {
  console.log (error);
});
