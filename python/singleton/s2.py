def Singleton(cls):
    _instance = {}

    def _singleton(*args, **kargs):
        if cls not in _instance:
            _instance[cls] = cls(*args, **kargs)
        return _instance[cls]

    return _singleton


@Singleton
class A(object):
    a = 1

    def __init__(self, x=0):
        self.x = x


a1 = A(2)
a2 = A(3)

if __name__ == "__main__":
    print ("a1 {}".format(a1))
    print ("a2 {}".format(a2))
    print ("a1 == a2 ? {}".format(a1 == a2))
