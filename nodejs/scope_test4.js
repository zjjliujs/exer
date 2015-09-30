function test(){
    var a = 1;
    function test(){
        var a;
        a = 2;
    }
    test();
    console.log (a);
}

test ();
