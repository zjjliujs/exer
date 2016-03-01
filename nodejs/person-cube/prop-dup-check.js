#!/opt/node/bin/node
const assert = require("assert"); 

function usage() {
    console.log ("To check property duplication");
    console.log ("prop-propgrp-consist-check <ontology-file>");
}

function check (props, progGrps) {
    //检查是否有重复的属性name
    var pns = [];
    props.forEach ((val) => {
        pns.push (val.name);
    });
    propGrps.forEach ((pg) => {
        pg.properties.forEach ((p) => {
            pns.push (p.name);
        });
    });
    console.log ("Total " + pns.length + " properties ");
    pns.sort();
    //console.log (pns);

    var dup = 0;
    pns.reduce ((pv, cv, ci, array) => {
        if (pv === cv) {
            console.log ("Found duplicate property name!");
            console.log (pv);
            ++dup;
        }
        return cv;
    }, null);
    if (dup){
        console.log ("Found " + dup + " duplication property names");
    }else{
        console.log ("Check passed, no duplicate property found");
    }
}

if (process.argv.length < 3) {
    usage();
    process.exit(1);
}

const fs = require('fs');
const fName = process.argv[2];
fs.readFile (fName, {encoding:'utf8', flag:'r'}, (err, data) => {
    if(err) {
        console.log ("Couldn't read file " + fName);
        process.exit (2);
    }

    var ontology = JSON.parse (data);
    var objTypes = ontology.ObjectTypes;
    objTypes.forEach ((val, idx, array) => {
        props = val.properties;
        assert(props);
        propGrps = val.propertyGroups;
        assert(propGrps);

        check(props,propGrps);
    });
});
