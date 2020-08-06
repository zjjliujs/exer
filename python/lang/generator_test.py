# -*- coding: UTF-8 -*-
def reverse(data):
    for index in range(len(data)-1, -1, -1):
        print("index:{}".format(index))
        yield data[index]

if __name__=="__main__":
    for char in reverse('golf'):
        print(char)
