# -*- coding: UTF-8 -*-
import logging

# assuming loglevel is bound to the string value obtained from the
# command line argument. Convert to upper case to allow the user to
# specify --log=DEBUG or --log=debug
numeric_level = getattr(logging, loglevel.upper(), None)
if not isinstance(numeric_level, int):
    raise ValueError('Invalid log level: %s' % loglevel)

logging.basicConfig(filename='example.log', filemode='w', level=numeric_level)
logging.debug('This message should go to the log file')
logging.info('So should this')
logging.warning('And this, too')
