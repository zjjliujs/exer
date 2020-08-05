# mylib.py
import logging
logger = logging.getLogger(__name__)

def do_something():
    logging.info('Doing something')
    logger.info('Doing something')
    logging.warning('%s before you %s', 'Look', 'leap!')
    logger.warning('%s before you %s', 'Look', 'leap!')
