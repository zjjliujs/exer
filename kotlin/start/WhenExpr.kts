fun describe(obj: Any): String =
	when(obj) {
		1 -> "One"
		"Hello" -> "Greeting"
		is Long -> "Long"
		!is String -> "Not a string"
		else -> "Unknown"
	}

val a = 1
println ("Description of $a is ${describe(a)}")

val s = "kkk"
println ("Description of $s is ${describe(s)}")
