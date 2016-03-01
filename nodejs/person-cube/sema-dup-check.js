#!/opt/node/bin/node
function usage (){
    console.log ("Check property sematic duplication");
    console.log ("sema-dup-check.js <sema-file>");
}

if (process.argv.length < 3) {
    usage ();
    process.exit (1);
}

function check_dup(semas) {
    var semaNames = [];
    semas.forEach ((val) => {
        semaNames.push (val.name);
    });

    semaNames.sort ();
    var dup = 0;
    semaNames.reduce ((pv, cv, ci, array) => {
        if (pv === cv) {
            console.log ("Found duplicate sema names");
            console.log (pv + " => " + cv);
            ++dup;
        }
        return cv;
    }, null);

    console.log ("Found " + dup + " duplication sema names");
}

const assert = require ("assert");
const fs = require ("fs");

const fName = process.argv[2];
fs.readFile (fName, {encoding:'utf-8', flag:'r'}, (err, data) => {
    if (err) {
        console.log ("Couldn't open file " + fName);
        process.exit (2);
    }

    var semaTypes = JSON.parse(data).PropertySemaTypes;
    assert (semaTypes);
    console.log ("Total " + semaTypes.length + " sema types" );

    check_dup (semaTypes);
});
