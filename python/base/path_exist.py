# -*- coding:utf-8 -*-
import os

pn = "not/exist/dir"
print ("{} exist ? {}".format(pn, os.path.exists(pn)))
print ("{} isfile? {}".format(pn, os.path.isfile(pn)))
print ("{} isdir? {}".format(pn, os.path.isdir(pn)))

print("\n")

pn = "path_exist.py"
print ("{} exist ? {}".format(pn, os.path.exists(pn)))
print ("{} isfile? {}".format(pn, os.path.isfile(pn)))
print ("{} isdir? {}".format(pn, os.path.isdir(pn)))

print("\n")

pn = "../base"
print ("{} exist ? {}".format(pn, os.path.exists(pn)))
print ("{} isfile? {}".format(pn, os.path.isfile(pn)))
print ("{} isdir? {}".format(pn, os.path.isdir(pn)))
