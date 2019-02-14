var http=require("http")

http.createServer(function (request, response) {
	response.writeHead(200, {'Content-Type': 'text/plain'});

	response.end("Hello, world\n");
}).listen(9091);

console.log("Http server running at http://127.0.0.1:9091");
