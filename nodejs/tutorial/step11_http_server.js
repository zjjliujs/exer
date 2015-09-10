var http=require("http");
var fs=require("fs");

var port = parseInt (process.argv[2]);
var fname = process.argv[3];

var server = http.createServer (function (request, response) {
  response.writeHead(200, {'content-type':'text/plain'});
  fs.createReadStream(fname).pipe (response);
});

server.listen(port);
