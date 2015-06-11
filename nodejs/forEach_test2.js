var a=[{value:1},{value:2},{value:3},{value:4},{value:5}];

function inc(data) {
    data.value++;
}

a.forEach (inc, null);
console.log(a);

var b = [];
b.forEach (inc, null);
console.log(b);
