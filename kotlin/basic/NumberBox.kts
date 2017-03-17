val a: Int = 10000
//deprecated!
//print(a === a) // Prints 'true'
val boxedA: Int? = a
val anotherBoxedA: Int? = a

println(boxedA === anotherBoxedA) // !!!Prints 'false'!!!
println(boxedA == anotherBoxedA) // Prints 'true'
