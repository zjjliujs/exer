var a = 1

var s1 = "a is $a"
println (s1)

a += 1;
println ("${s1.replace("is", "was")}, but now is $a")
