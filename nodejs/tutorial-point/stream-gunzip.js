var fs = require("fs");
var zlib = require('zlib');

// Decompress the file input.txt.gz to input.txt
fs.createReadStream('data/input.txt.gz')
  .pipe(zlib.createGunzip())
  .pipe(fs.createWriteStream('data/input.txt'));
  
console.log("File Decompressed.");
