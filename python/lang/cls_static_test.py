# -*- coding: utf-8 -*-

class MyClass:
    @staticmethod
    def static_fun():
        print('静态方法')

    @classmethod
    def class_fun(cls):
        print('类方法')
        print(cls)

    # 普通方法
    def method_fun(self):
        print('普通方法')
        print(self)



MyClass.static_fun()
MyClass.class_fun()

C = MyClass()
C.static_fun()
C.class_fun()
C.method_fun()
