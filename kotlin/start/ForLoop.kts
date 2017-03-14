val l = listOf ("apple", "banana", "kiwi")

for (item in l)
	println (item)

for (idx in l.indices) {
	println ("item at ${idx} is ${l[idx]}")
}
