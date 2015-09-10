var net = require ("net");
var strftime = require ("strftime");

var server = net.createServer (function(socket){
  var s = strftime("%Y-%m-%d %H:%M", new Date());
  socket.write (s);
  socket.end();
});

var port = parseInt (process.argv[2]);
server.listen(port);
