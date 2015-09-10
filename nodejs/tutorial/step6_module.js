var fs = require("fs");
var path = require("path");

module.exports = function (dir, filter, callback) {
  fs.readdir(dir, function(err, list) {
    if (err != null) {
      return callback (err, null);
    }
    if (filter != null && filter.length != 0) {
      var ext = "." + filter;
      var result = list.filter(function (file){
        return path.extname(file) == ext;
      });
      return callback (null, result);
    }
    return callback (null, list);
  })
};
