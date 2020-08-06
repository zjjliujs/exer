# -*- coding: UTF-8 -*-
class Wav:

    def __init__(self):
        print ("{} init".format(__name__))

    def read (self):
        print("{} read".format(__name__))

print("module: {}".format(__name__))
