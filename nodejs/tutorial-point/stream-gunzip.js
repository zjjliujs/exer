var fs = require("fs");
var zlib = require('zlib');

var gunzipStream = zlib.createGunzip();

// Decompress the file input.txt.gz to input.txt
fs.createReadStream('data/input.txt.gz')
  .pipe(gunzipStream);
  //.pipe(fs.createWriteStream('data/input.txt'));

gunzipStream.on("data", function(chunk){
    console.log(chunk.toString());
});
  
console.log("File Decompressed.");
