import unittest
import s3

import time
import threading
objs = []
for i in range(10):
    objs.append(None)

def task(arg):
    obj = s3.Singleton.instance()
    objs[arg] = obj;
    print(obj)


class S2Test(unittest.TestCase):

    def setUp(self):
        self.a1 = s3.Singleton.instance()
        self.a2 = s3.Singleton.instance()

    def test_single(self):
        self.assertEqual(self.a1, self.a2)

    def test_thread_single(self):
        for i in range(10):
            t = threading.Thread(target=task,args=[i,])
            t.start()
        
        for obj in objs[1:10]:
            self.assertEqual(objs[0], obj)



if __name__ == "__main__":
    unittest.main()
