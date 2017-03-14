val listWithNulls: List<String?> = listOf("A", null)

for (item in listWithNulls) {
	item?.let {println (it)} //print A and ignores null
}
