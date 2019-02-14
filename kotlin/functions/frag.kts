fun frag(content: String) = {declare: String -> """
	$declare
	xxxxxxxxxxx
	$content
"""}

val s = frag("hello")("world")
print ("$s")
