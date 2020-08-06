# -*- coding: UTF-8 -*-
a = sum(i*i for i in range(10))                 # sum of squares
print (a)

xvec = [10, 20, 30]
yvec = [7, 5, 3]
b = sum(x*y for x,y in zip(xvec, yvec))         # dot product
print (b)

from math import pi, sin
sine_table = {x: sin(x*pi/180) for x in range(0, 91)}
print(sine_table)


page=[]
line="Hello, world!"
page.append(line)
line="Hello, python!"
page.append(line)
line="Welcome, python!"
page.append(line)
unique_words = set(word  for line in page  for word in line.split())
print(unique_words) 

class Student:
    pass

graduates=[]
student = Student()
student.gpa = 100
student.name= "ljs"
graduates.append(student)
student = Student()
student.gpa = 80
student.name= "zjj"
graduates.append(student)
valedictorian = max((student.gpa, student.name) for student in graduates)
print(valedictorian)

data = 'golf'
l = list(data[i] for i in range(len(data)-1, -1, -1))
print (l)
