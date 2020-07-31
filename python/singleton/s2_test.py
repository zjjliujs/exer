import unittest
import s2

class S2Test(unittest.TestCase):

    def setUp(self):
        self.a1 = s2.A()
        self.a2 = s2.A()

    def test_single(self):
        self.assertEqual(self.a1, self.a2)


if __name__ == "__main__":
    unittest.main()
