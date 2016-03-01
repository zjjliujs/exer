#!/opt/node/bin/node
function usage() {
    console.log ("Property and property sema consist check");
    console.log ("usage:prop-sema-consist-check.js <prop-file> <sema-file>");
}

if (process.argv.length < 4){
    usage ();
    process.exit (1);
}

function retrieveProps (ontology) {
    var r = [];
    ontology.ObjectTypes.forEach ((ot) => {
        var ps = ot.properties;
        ot.propertyGroups.forEach ((val) => {
            ps = ps.concat (val.properties);
        });
        r = r.concat(ps);
    });
    return r;
}

const assert = require ("assert");
const fs = require ("fs");

const pfName = process.argv[2];
const cfName = process.argv[3];
fs.readFile (pfName, {encoding:'utf-8'}, (err,data) => {
    if (err) {
        console.log ("Couldn't read file " + pfName);
        process.exit(2);
    }
    var ontology = JSON.parse(data);
    var props = retrieveProps (ontology);
    //console.log (props);
    fs.readFile (cfName, {encoding:'utf-8'}, (err, data) => {
        if (err) {
            console.log ("Couldn't read file " + pfName);
            process.exit(2);
        }
        var semas = JSON.parse(data).PropertySemaTypes;
        //console.log (semas);
        props.forEach ((p) => {
            var s = semas.find ((v) => {
                return v.name == p.semaType;
            });
            if (!s) {
                console.log ("Couldn't find sema type " + p.semaType);
                return;
            }
            if (s.baseType != p.baseType) {
                console.log (p.semaType + "'s baseType don't match " + p.baseType + " <--> " + s.baseType);
            }
        });
    });
});
