# -*- coding: utf-8 -*-

from SoapClientLoginDialog import *
from SoapClientWindow import *

import sys
from PyQt5.QtWidgets import *
from PyQt5.QtCore import QSize, QRect
from zeep import Client

CLIENT_TITLE  = 'Bate-Papo por Tópicos'
CLIENT_WIDTH  = 300
CLIENT_HEIGHT = 400

class SoapClient():
    def __init__(self, soapServiceURL):
        self.mainApp = QApplication(sys.argv)
        self.soapConsumer = Client(soapServiceURL)
        self.loginDialog = SoapClientLoginDialog(self.soapConsumer)
        
        if self.loginDialog.exec() == QDialog.Accepted:
            self.mainWindow = SoapClientWindow(
                self.soapConsumer,
                CLIENT_TITLE, 
                CLIENT_WIDTH, 
                CLIENT_HEIGHT,
                self.loginDialog.getLoginResponse().token)
            sys.exit(self.mainApp.exec())

if __name__ == '__main__':
    soapServiceURL = 'http://192.168.1.102:8080/ApiSOAP-V100/ApiSoap?wsdl'
    SoapClient(soapServiceURL)
