import sys
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('action', choices=['list', 'search'])
parser.add_argument('--foo',  required=True)
parser.add_argument('--bar') 
parser.add_argument('--baz')
args = parser.parse_args()
print (args)
