fun getStringLength(obj: Any):Int? {
	if (obj is String) {
		//obj is automatically cast to String in this branch
		return obj.length
	}
	//obj is still of type Any outside of type check branch
	return null
}

val s = "Hello,world!"
println ("$s has ${getStringLength(s)?:-1} characters")

val i = 10
println ("$i has ${getStringLength(i)?:-1} characters")
