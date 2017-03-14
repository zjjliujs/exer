var ns:String? = "abc"


var nsl = ns?.length ?: -1
println ("${ns} has ${nsl} characters")

ns = null
nsl = ns?.length ?: -1
println ("${ns} has ${nsl} characters")
