var http=require("http");
var map=require("through2-map");
var url=require("url");

var port = parseInt (process.argv[2]);

var server = http.createServer (function (request, response) {
  if(request.method != "GET")
    return response.end ("Send me a GET!");

  var pathname = url.parse(request.url).pathname;
  var qstr = url.parse(request.url).query;
  var tstr = qstr.substring(4);
  var d = new Date(tstr);
  var o = null;

  switch (pathname) {
    case "/api/parsetime":
        o = {hour:d.getHours(), minute: d.getMinutes(), second: d.getSeconds()};
      break;

    case "/api/unixtime":
        o = {unixtime:d.getTime()};
      break;

    default:
      console.log ("Unknow path: " + pathname);
      o = {error:"Unknow path: " + pathname};
      break;
  }

  response.writeHead (200, {'Content-Type':'application/json'});
  response.write (JSON.stringify(o));
  response.end ();
});

server.listen(port);
