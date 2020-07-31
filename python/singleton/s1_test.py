import unittest

from s1 import singleton as si1
from s1 import singleton as si2

class TestS1(unittest.TestCase):
    
    def test_single(self):
        self.assertEqual(si1, si2)


if __name__ == "__main__":
    unittest.main()
