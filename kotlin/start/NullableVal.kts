var nns:String = "abc"
var ns:String? = "abc"
//compile error
//nns = null
ns = null

println ("${nns} has ${nns.length} characters!")
//compile error
//println ("${ns} has ${ns.length} characters!")

val nsl = if (ns != null) (ns as String).length else -1
println ("${ns} has ${nsl} characters!")
