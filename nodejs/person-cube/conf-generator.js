#!/opt/node/bin/node
function usage() {
    console.log ("Usage: conf-generator.js <ontology-file>");
    console.log ("Generator person cube app conf from person ontology file.");
}

if (process.argv.length < 3) {
    usage();
    process.exit (1);
}

const fs = require('fs');
const assert = require ('assert');

const pfName = process.argv[2];

fs.readFile (pfName, {encoding:'utf-8'}, (err,data) => {
    if (err) {
        console.log ("Couldn't read file " + pfName);
        process.exit (2);
    }

    var ontologies = JSON.parse(data);
    assert (ontologies.ObjectTypes);
    assert (ontologies.ObjectTypes.length > 0);
    var ot = JSON.parse(data).ObjectTypes[0];
    assert (ot);

    

});
