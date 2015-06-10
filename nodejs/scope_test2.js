function f1() {
  var a = 10;
  function f1_f1() {
    var a_a = 20;
  }
  f1_f1();
}

function f2() {
  console.log(typeof(a));
  console.log(typeof(a_a));
}

f1();
f2();
