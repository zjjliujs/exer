#!/opt/node/bin/node
if (process.argv.length < 3) {
    console.log ("Usage: pgroup-conf-generator.js <ontology-file>");
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
    assert (ontology.ObjectTypes[0].propertyGroups);
    var pgs = ontology.ObjectTypes[0].propertyGroups;

    pgs.forEach (function (pg){
        //所有不属于属性组的属性
        var props = pg.properties;
        //console.log (props.length);
        //过滤掉属于header的属性
        var props = props.filter(function (v) {
            var s = v.semaType;
            return (s.slice(-3) != '_vc');
        });
        //产生配置文件
        var conf=[];
        props.forEach (function (v){
            var o = {name:v.name, title:v.semaType};
            conf.push(o);
        });
        console.log (pg.name);
        console.dir (conf);
    });
});
