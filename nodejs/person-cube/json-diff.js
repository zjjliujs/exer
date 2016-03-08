#!/opt/node/bin/node
if (process.argv.length < 4) {
    console.log ("Usage: json-diff.js <json-file1> <json-file2>");
    console.log ("display the difference of the 2 files");
    process.exit(0);
}

var f1 = process.argv[2];
var f2 = process.argv[3];

//只检查属性是否只存在于obj1,不存在于obj2中
function existDiff (obj1, obj2) {
    for (i in obj1) {
        if (typeof(obj2[i]) == "undefined"){
            console.log (i + " only exist in " + f2);
            continue;
        }
        var so1 = obj1[i];
        var so2 = obj2[i];
        if (so1.constructor == so2.constructor  
                && (so1.constructor  == Object || so2.constructor == Number))
        {
            existDiff (so1, so2);
        }
    }
}

function objDiff (obj1, obj2) {
    for (i in obj1) {
        //检查属性是否存在
        //console.log ("Compare " + i);
        if(typeof(obj2[i]) == "undefined"){
            console.log (i + " only exist in " + f1);
            continue;
        }
        var so1 = obj1[i];
        var so2 = obj2[i];
        //检查null
        if (so1 == null && so2 != null) {
            console.log (i + " in " + f1 + " is null, but in " + f2 + " is not null");
            continue;
        }
        if (so1 != null && so2 == null) {
            console.log (i + " in " + f2 + " is null, but in " + f1 + " is not null");
            continue;
        }
        //检查类型的一致性
        if (so1.constructor != so2.constructor) {
            console.log (i + " in f1 is " + so1.constructor.name + " But  in f2 is " + so2.constructor.name);
            continue;
        }
        //类型一致,检查是否相同
        if (so1.constructor == Number || so1.constructor == String || so1.constructor == Boolean){
            if (so1 != so2){
                console.log (">" + f1 + "-->" + i + ":" + so1);
                console.log ("<" + f2 + "-->" + i + ":" + so2);
            }
            continue;
        }
        //对Array,Object类型递归比较
        objDiff (so1, so2);
    }
}


var fs = require('fs');
var assert = require ('assert');

fs.readFile (f1, {encoding:'utf8', flag:'r'}, (err, data) => {
    assert (!err);

    var obj1 = JSON.parse(data);
    fs.readFile (f2, {encoding:'utf8', flag:'r'}, (err, data) => {
        assert (!err);

        var obj2 = JSON.parse(data);

        objDiff (obj1, obj2);
        //检查存在于obj2而不存在于obj1中的属性
        existDiff (obj2, obj1);
    });
});
