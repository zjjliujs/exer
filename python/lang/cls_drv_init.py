# -*- coding: UTF-8 -*-
class Base(object):
    
    def __init__(self, msg):
        print ("Base init:{}".format(msg))


class Drived(Base):

    def __init__(self):
        super(Drived, self).__init__("msg from derived class!")
        print ("Derived init")


d = Drived()
