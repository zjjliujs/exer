var fs = require("fs");
var path = require("path");

var dir = process.argv[2];
var filter = process.argv[3];
fs.readdir(dir, function(err, list) {
  if (err != null && err != 0)
    console.log (err);
  else {
    if (filter != null && filter.length != 0) {
      var ext = "." + filter;
      list.forEach (function (file){
        if (path.extname(file) === ext)
          console.log(file);
      })
    }else{
      for (var i=0; i<list.length; ++i)
        console.log(list[i]);
    }
  }
})
