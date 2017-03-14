/*
fun sum(a:Int, b:Int):Int {
	return a + b
}
*/

fun sum(a:Int, b:Int) = a + b

fun main(args:Array<String>) {
	val a = 3
	val b = 5
	val r = sum (a, b)
	println ("$a + $b is $r")

	printSum (5, 7)
}

fun printSum(a:Int, b:Int) {
	println ("$a + $b is ${a+b}")
}
