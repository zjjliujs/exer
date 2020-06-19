#!/usr/bin/python3
#   -*- coding: utf-8 -*- 

import struct
#import small_map as mapData
import huge_map as mapData

outFile = "map_data.bin"

with open(outFile, 'wb')as fp:
    for x in mapData.data: 
        a = struct.pack('b', x)
        fp.write(a)
 
print('Write %s done!' % (outFile))
print('To view the output content, use below command:');
print('------------------------------------');
print('od -t x1 %s' % (outFile))
print('------------------------------------');
