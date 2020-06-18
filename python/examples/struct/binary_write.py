#!/usr/bin/python3
#   -*- coding: utf-8 -*- 

import struct

outFile = "out_bin.bin"

list_dec = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
with open(outFile, 'wb')as fp:
    for x in list_dec:
        a = struct.pack('B', x)
        fp.write(a)
 
print('Write %s done!' % (outFile))
print('To view the output content, use below command:');
print('------------------------------------');
print('od -t x1 %s' % (outFile))
print('------------------------------------');
