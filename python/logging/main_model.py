import logging
from log_init import log_init
import lib.auxiliary_module as subModule

log_init()

logger = logging.getLogger()

logger.info('creating an instance of subModule.Auxiliary')
a = subModule.Auxiliary()
logger.info('created an instance of subModule.Auxiliary')

logger.info('calling subModule.Auxiliary.do_something')
a.do_something()
logger.info('finished subModule.Auxiliary.do_something')

logger.info('calling subModule.some_function()')
subModule.some_function()
logger.info('done with subModule.some_function()')
