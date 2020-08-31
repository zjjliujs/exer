# -*- coding:utf-8 -*-
import os

if __name__ == "__main__":
    path = "e:test/module/log"
    folder_path, file_name = os.path.split(path)
    print("需要分离的文件路径是：" + path)
    print("\n分离后的结果是：")
    print("文件名：" + file_name + "\t\t\t\t文件夹路径：" + folder_path)
