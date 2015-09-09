var f1 = function () {
    this.a = {};

    this.f1_1 = function () {
        this.a.p = "Hello";
    }

    this.f1_2 = function () {
        this.a = {};
        this.a.p = "World";
    }
};

var o =new f1();
o.f1_1();
var u = new o.f1_2();

console.log(o.a);
console.log(u.a);

o.f1_2();
console.log(o.a);
console.log(u.a);
