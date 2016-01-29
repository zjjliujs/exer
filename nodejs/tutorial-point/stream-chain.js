var fs = require("fs");
var zlib = require('zlib');

// Compress the file input.txt to input.txt.gz
fs.createReadStream('data/input.txt')
  .pipe(zlib.createGzip())
  .pipe(fs.createWriteStream('data/input.txt.gz'));
  
console.log("File Compressed.");
