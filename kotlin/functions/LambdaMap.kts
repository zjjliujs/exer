fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
    val result = arrayListOf<R>()
    for (item in this)
        result.add(transform(item))
    return result
}

val ints = listOf(1,2,3,4,5,6)
val doubled = ints.map {it -> it * 2}

for (d in doubled) {
	println ("$d")
}
