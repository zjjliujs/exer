class Dog:

    def __init__(self, name):
        self.name = name
        self.tricks = []    # creates a new empty list for each dog

    def add_trick(self, trick):
        self.tricks.append(trick)

d = Dog('Fido')
d.add_trick('roll over')
print("{} can {}".format(d.name, d.tricks))

e = Dog('Buddy')
e.add_trick('play dead')
print("{} can {}".format(e.name, e.tricks))
