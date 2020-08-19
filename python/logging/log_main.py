# -*- coding: UTF-8 -*-
# myapp.py
import logging
import log_mod as mylib

def main():
    logging.basicConfig(filename='example.log', level=logging.INFO)
    logging.info('Started')
    mylib.do_something()
    logging.info('Finished')

if __name__ == '__main__':
    main()
