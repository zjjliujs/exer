import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--bar', action='store_true')
parser.add_argument('--baz', action='store_false')
args = parser.parse_args()
print (args)
