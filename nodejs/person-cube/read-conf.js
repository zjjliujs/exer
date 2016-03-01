#!/opt/node/bin/node
if (process.argv.length < 3) {
    console.log ("Usage: read-conf.js <app-conf-file>");
    process.exit(0);
}

var fName = process.argv[2];

var fs = require('fs');

fs.readFile (fName, {encoding:'utf8', flag:'r'}, (err, data) => {
    if (err) {
        console.log ("Couldn't read file " + fName);
        process.exit(1);
    }

    var appConf = JSON.parse(data);
    for (p in appConf) {
        console.log (p);
        console.log (appConf[p]);
    }
});
