#!python
# -*- coding=utf-8 -*-

import caffe
caffe.set_mode_cpu()
solver = caffe.SGDSolver('examples/mnist/lenet_solver.prototxt')
#solver.solve()

print ('iter: %d' % solver.iter)
solver.step(1)
input_data = solver.net.blobs['data'].data
loss = solver.net.blobs['loss'].data
accuracy = solver.test_nets[0].blobs['accuracy'].data
print ('iter: %d loss: %f, accuracy:%d' % (solver.iter, loss, accuracy))
