CAFFE_ROOT=~/caffe-cpu

blob_demo: blob_demo.cpp
	g++ -o $@ $^ -I $(CAFFE_ROOT)/include -DCPU_ONLY -I $(CAFFE_ROOT)/build/src -L $(CAFFE_ROOT)/build/lib -lcaffe -lglog

.PHONY:clean
clean:
	rm -rf *.o blob_demo
