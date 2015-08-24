function f1() {
  a = 10;
  function f1_f1() {
    a_a = 20;
  }
  f1_f1();
}

function f2() {
  console.log(a);
  console.log(a_a);
}

f1();
f2();
