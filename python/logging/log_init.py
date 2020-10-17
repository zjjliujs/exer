import logging

def log_init():
    # create logger with 'spam_application'
    logger = logging.getLogger()
    logger.setLevel(logging.DEBUG)
    
    # create file handler which logs even debug messages
    fh = logging.FileHandler('spam.log', 'w')
    fh.setLevel(logging.DEBUG)
    
    # create console handler with a higher log level
    ch = logging.StreamHandler()
    ch.setLevel(logging.ERROR)
    
    # create formatter and add it to the handlers
    formatter = logging.Formatter('%(asctime)s- %(levelname)s - %(name)s [%(filename)s:%(lineno)d] - %(message)s - ')
    fh.setFormatter(formatter)
    ch.setFormatter(formatter)
    
    # add the handlers to the logger
    logger.addHandler(fh)
    logger.addHandler(ch)
    
