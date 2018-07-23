import math
import decimal
import PyPDF2
import imaplib

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
        