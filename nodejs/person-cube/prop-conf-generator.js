#!/opt/node/bin/node
if (process.argv.length < 3) {
    console.log ("Usage: basic-conf-generator.js <ontology-file>");
    process.exit(0);
}

var oFName = process.argv[2];

var fs = require('fs');
var assert = require('assert');

fs.readFile(oFName, {encoding:'utf-8', flag:'r'}, (err, data) => {
    assert (!err);

    var ontology = JSON.parse(data);
    assert (ontology.ObjectTypes);
    assert (ontology.ObjectTypes.length > 0);
    var ots = ontology.ObjectTypes[0];
    //所有不属于属性组的属性
    var props = ots.properties;
    //console.log (props.length);
    //产生配置文件
    var conf=[];
    props.forEach (function (v){
        var o = {sema:v.semaType};
        conf.push(o);
    });
    console.dir(conf);
});
