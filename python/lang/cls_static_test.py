# -*- coding: utf-8 -*-

class MyClass:
    @staticmethod
    def static_fun(args):
        print ('静态方法')
        print (args)

    @classmethod
    def class_fun(cls):
        print('类方法')
        print(cls)

    # 普通方法
    def method_fun(self):
        print('普通方法')
        print(self)
        MyClass.static_fun(3)



MyClass.static_fun(1)
MyClass.class_fun()

C = MyClass()
C.static_fun(2)
C.class_fun()
C.method_fun()
