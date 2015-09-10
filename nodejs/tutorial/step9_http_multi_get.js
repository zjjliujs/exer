var http = require ("http");
var BufferList = require ("bl");

var urls = process.argv.slice (2, 5);
var readyCount = 0;
var buffers = new Array();

for (var i=0; i<3; ++i) {
  do_get (i);
}

function do_get(i) {
  var n = i;
  var req = http.get (urls[n] , function (response) {
    response.pipe (BufferList (function(err, data) {
      buffers[n] = data;
    }));
    response.on ("end", function(data) {
      ++readyCount;
      if (readyCount == 3) {
        output_all (buffers);
      }
    });
  })

  req.on ("error", function (error) {
    console.log (error);
  });
}

function output_all (buffers) {
  for (var i=0; i<3; ++i) {
    console.log (buffers[i].toString("utf-8"));
    //console.log (buffers[i].length + "B from " + urls[i]);
  }
}
