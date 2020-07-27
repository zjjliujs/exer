#!python
# -*- coding=utf-8 -*-

import caffe
caffe.set_mode_cpu()
solver = caffe.SGDSolver('examples/mnist/lenet_solver.prototxt')
#solver.solve()

iter = solver.iter
while iter<10000:
    solver.step(1)
    iter = solver.iter
    input_data = solver.net.blobs['data'].data
    loss = solver.net.blobs['loss'].data
    accuracy = solver.test_nets[0].blobs['accuracy'].data
    print 'iter:', iter, 'loss:', loss,'accuracy:',accuracy
