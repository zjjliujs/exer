import fibo_mod as fm

print ("model name {}".format(fm.__name__))

print ("reload module!")

import imp
imp.reload(fm)
