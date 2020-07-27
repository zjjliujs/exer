#!python
# -*- coding=utf-8 -*-

import caffe
caffe.set_mode_cpu()
solver = caffe.SGDSolver('examples/mnist/lenet_solver.prototxt')
#solver.solve()

it = solver.iter
while it<10000:
    solver.step(1)
    it = solver.iter
    input_data = solver.net.blobs['data'].data
    loss = solver.net.blobs['loss'].data
    accuracy = solver.test_nets[0].blobs['accuracy'].data
    print ('iter: %d loss: %f, accuracy:%d' % (it, loss, accuracy))
