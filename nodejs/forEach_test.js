var a=[1,2,3,4,5];
var o={};

function put(data) {
    if (! this.datas)
        this.datas = [];
    this.datas.push(data);
}

a.forEach (put, o);

console.log(o);
