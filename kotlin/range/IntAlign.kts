fun Int.align() = (0..31).first{(this ushr it) == 0}.let{ 1 shl it }

var v = 1.align()
print ("$v\n")

v = 2.align()
print ("$v\n")

v = 4.align()
print ("$v\n")

v = 1024.align()
print ("$v\n")
