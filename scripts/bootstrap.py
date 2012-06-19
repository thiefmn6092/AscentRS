#
# Essential functions and variables for all scripts.
#
# Author: thiefmn6092
# Version: 1.0.0.0
#
from org.slf4j import LoggerFactory

# A utility class for logging in scripts.
class Logger():
    def __init__(self):
        self.logger = LoggerFactory.getLogger(self.__class__.__name__)
    def info(self, message):
        self.logger.info(message)

# The global logger instance.
LOGGER = Logger()