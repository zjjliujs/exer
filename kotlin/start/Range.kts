fun inRange (x:Int, l:Int, h:Int):String {
	if (x in l..h) {
		return "$x fits in $l..$h"
	} else {
		return "$x don't fits in $l..$h"
	}
}

println (inRange (1, 1, 10))
println (inRange (10, 1, 10))
