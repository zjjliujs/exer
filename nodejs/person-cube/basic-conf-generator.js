#!/opt/node/bin/node
if (process.argv.length < 4) {
    console.log ("Usage: basic-conf-generator.js <head-conf-file> <ontology-file>");
    process.exit(0);
}

var hcFName = process.argv[2];
var oFName = process.argv[3];

var fs = require('fs');
var assert = require('assert');

fs.readFile (hcFName, {encoding:'utf-8', flag:'r'}, (err, data) => {
    assert (!err);

    var hcs = JSON.parse(data);
    fs.readFile(oFName, {encoding:'utf-8', flag:'r'}, (err, data) => {
        assert (!err);
        
        var ontology = JSON.parse(data);
        assert (ontology.ObjectTypes);
        assert (ontology.ObjectTypes.length > 0);
        var ots = ontology.ObjectTypes[0];
        //所有不属于属性组的属性
        var props = ots.properties;
        //console.log (props.length);
        //过滤掉属于header的属性
        var basicProps = props.filter(function (v) {
            var h = hcs.find(function (vv){
                return vv.sema == v.semaType;
            });  
            var s = v.semaType;
            return !h && (s.slice(-3) != '_vc');
        });
        //console.log (basicProps.length);
        //产生配置文件
        var conf=[];
        basicProps.forEach (function (v){
            var o = {sema:v.semaType};
            conf.push(o);
        });
        console.dir(conf);
    });
});
