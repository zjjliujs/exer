# -*- coding: UTF-8 -*-
import abc

class PluginBase(object):
    __metaclass__ = abc.ABCMeta

    def base_func():
        print("base function called!")

    def base_method(self):
        print("base method called!")

    @abc.abstractmethod
    def load(self, input):
        """Retrieve data from the input source and return an object."""
        return

    @abc.abstractmethod
    def save(self, output, data):
        """Save the data object to the output."""
        return
