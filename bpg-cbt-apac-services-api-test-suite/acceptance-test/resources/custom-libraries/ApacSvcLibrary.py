import math
import decimal
import PyPDF2
import imaplib
from robot.libraries.String import String
from robot.libraries.BuiltIn import BuiltIn
from decimal import Decimal, ROUND_HALF_UP, ROUND_HALF_DOWN, ROUND_DOWN, ROUND_UP

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

    def fix_decimal_places(self, n):
        f = n[::-1].find('.')
        if f == 1:
            return str(n) + '0'
        else:
            return n
        
    def get_pdf_text(self, dr):
        pdfFileObj = open(dr, 'rb')
        pdfReader = PyPDF2.PdfFileReader(pdfFileObj)
        pageObj = pdfReader.getPage(0)
        return pageObj.extractText()
    
    def format_number(self, num):
        return "{:,}".format(num)
    
    def connect_to_outlook(self):
        url = 'swnv02ix00232.int.carlsonwagonlit.com'
        print (url)
        conn = imaplib.IMAP4_SSL(url,993)
        print (url)
        conn.starttls()
        user = 'CWT\\U001SIP'
        password = 'wefight09$'
        conn.login(user,password)
        conn.select('INBOX')
        results,data = conn.search(None,'ALL')
        msg_ids = data[0]
        msg_id_list = msg_ids.split()
        return msg_id_list
    
    def round_to_nearest_dollar(self, amount, country, round_type=None):
       """
       If round type is not specified, it will be rounded according to standard
       If country is SG, round value will return float else returns integer
       | ${round_down_value}| round to nearest dollar | 123.34 | SG | down |
       | ${round_up_value}  | round to nearest dollar | 123.34 | HK | up   |
       | ${round_value}     | round to nearest dollar | 123.34 | IN |      |

       >>
       ${round_down_value} = 2334.00
       ${round_up_value} = 2335.00
       ${round_value} = 2334.00
       """
       round_type_dict= {'up':'ROUND_UP', 'down':'ROUND_DOWN'}
       amount_in_decimal = Decimal(amount)
       round_type = round_type_dict.get(str(round_type).lower(), 'ROUND_HALF_UP')
       round_value =  amount_in_decimal.quantize(Decimal('1'), rounding=round_type).quantize(Decimal('0.01'))
       """return round_value if country == 'SG' else int(round_value)"""
       return int(round_value)
        