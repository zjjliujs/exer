var http = require ("http");

var url = process.argv[2];
var req = http.get (url , function (response) {
  response.on ("data", function (data){
    var str = data.toString('utf-8');
    console.log (str);
  });
})

req.on ("error", function (error) {
  console.log (error);
});
