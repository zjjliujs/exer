class Base(object):
    
    def __init__(self):
        print ("Base init")


class Drived(Base):

    def __init__(self):
        super(Drived, self).__init__()
        print ("Derived init")


d = Drived()
