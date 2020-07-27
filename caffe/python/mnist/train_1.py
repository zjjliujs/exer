#!python
# -*- coding=utf-8 -*-

import caffe
caffe.set_mode_cpu()
solver = caffe.SGDSolver('examples/mnist/lenet_solver.prototxt')
solver.solve()
