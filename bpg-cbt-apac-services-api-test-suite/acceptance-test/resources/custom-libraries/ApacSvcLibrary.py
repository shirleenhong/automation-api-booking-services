import math
import decimal

class ApacSvcLibrary:
    ROBOT_LIBRARY_SCOPE = 'TEST CASE'
    def __init__(self):
        self._test = None
        
    def round_down(self, n, d=8):
        n = float(n)
        d = int(d)
        d = int('1' + ('0' * d))
        if d == 1:
            return int(math.floor(n * d)/d)
        else:
            return math.floor(n * d)/d

    def round_up(self, n, d=8):
        n = float(n)
        d = int(d)
        d = int('1' + ('0' * d))
        if d == 1:
            return int(math.ceil(n * d)/d)
        else:
            return math.ceil(n * d)/d