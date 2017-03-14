var ns:String? = "abc"

var nsl = ns!!.length
println ("${ns} has ${nsl} characters")

ns = null
nsl = ns!!.length
println ("${ns} has ${nsl} characters")
