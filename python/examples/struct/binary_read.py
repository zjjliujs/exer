#!/usr/bin/python3
#   -*- coding: utf-8 -*- 

import struct

inFile = "out_bin.bin"

content=[]
try:
    with open(inFile, 'rb')as f:
        d = f.read(1);
        while (len(d) > 0):
            #a is tuple!
            a = struct.unpack('B', d)
            content.append(a[0])
            d = f.read(1)
    print('------------------------------------');
    print('content of %s' % (inFile))
    print(content)
    print('------------------------------------');

except FileNotFoundError as err:
    print(err);
 
