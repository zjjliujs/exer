class Parent:

    def m (self):
        print ("Parent method!")


class Child(Parent):

    def m (self):
        Parent.m(self)
        print ("Child method!")

c = Child()
c.m()
