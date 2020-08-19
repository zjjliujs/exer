# -*- coding: UTF-8 -*-
import logging
import logging.config
import os.path as path

log_path='logging.conf'
#log_file_path = path.join(path.dirname(path.abspath(__file__)), log_path)
#print(log_file_path)
#logging.config.fileConfig(log_file_path)
logging.config.fileConfig(log_path)

# create logger
logger = logging.getLogger('simpleExample')

# 'application' code
logger.debug('debug message')
logger.info('info message')
logger.warning('warn message')
logger.error('error message')
logger.critical('critical message')
