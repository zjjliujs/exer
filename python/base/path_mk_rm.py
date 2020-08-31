# -*- coding:utf-8 -*-
import os

pn = "path/to/mk"

print ("before makedirs")
print ("{} exist ? {}".format(pn, os.path.exists(pn)))
print ("{} isfile? {}".format(pn, os.path.isfile(pn)))
print ("{} isdir? {}".format(pn, os.path.isdir(pn)))
print("\n")

os.makedirs(pn)

print ("after makedirs") 
print ("{} exist ? {}".format(pn, os.path.exists(pn)))
print ("{} isfile? {}".format(pn, os.path.isfile(pn)))
print ("{} isdir? {}".format(pn, os.path.isdir(pn)))
print("\n")

os.removedirs(pn)

print ("after removedirs")
print ("{} exist ? {}".format(pn, os.path.exists(pn)))
print ("{} isfile? {}".format(pn, os.path.isfile(pn)))
print ("{} isdir? {}".format(pn, os.path.isdir(pn)))
print("\n")
