val fruits = listOf ("Apple", "Pinapple", "Kiwi", "Banana", "Watermelon", "Pear", "Lemon", "Grape", "Stawberry")

fruits.filter { it.length > 6}
	.sortedBy { it }
	.map { it.toUpperCase() }
	.forEach { println (it) }
