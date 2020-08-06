# -*- coding: UTF-8 -*-
import abc
from abc_base import PluginBase

class SubclassImplementation(PluginBase):

    def load(self, input):
        return input.read()

    def save(self, output, data):
        return output.write(data)

    def call_test(self):
        PluginBase.base_func()
        self.base_method()

if __name__ == '__main__':
    a = SubclassImplementation()
    print ('Subclass:{}'.format(issubclass(SubclassImplementation, PluginBase)))
    print ('Instance:{}'.format(isinstance(a, PluginBase)))
    a.call_test()
