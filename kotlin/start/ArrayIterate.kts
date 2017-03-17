val a = arrayOf('a', 'b', 'c', 'd')

for ((index,value) in a.withIndex()) {
	println ("The element at ${index} is ${value}")
}
