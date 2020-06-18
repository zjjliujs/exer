#!/usr/bin/python3
#   -*- coding: utf-8 -*- 

import struct
import small_map

inFile = "map_data.bin"

try:
    content=[]
    with open(inFile, 'rb')as f:
        d = f.read(1);
        while (len(d) > 0):
            #a is tuple!
            a = struct.unpack('b', d)
            content.append(a[0])
            d = f.read(1)
    if content == small_map.data: 
        print("OK!Content read from %s is same as small_map.data" % inFile)
    else:
        print("Error!Content read from %d is different with small_map.data")

except FileNotFoundError as err:
    print(err);
 
