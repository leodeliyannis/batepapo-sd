# -*- coding: utf-8 -*-

import socket
from PyQt5.QtWidgets import *
from PyQt5.QtCore import QSize, QRect

class SoapClientLoginDialog(QDialog):
    def __init__(self, soapConsumer, parent=None):
        super(SoapClientLoginDialog, self).__init__(parent)
        self.soapConsumer = soapConsumer
        self.layout = QVBoxLayout(self)
        
        self.textName = QLineEdit(self)
        self.layout.addWidget(self.textName)
        
        self.textPass = QLineEdit(self)
        self.textPass.setEchoMode(QLineEdit.Password)
        self.layout.addWidget(self.textPass)
        
        self.buttonLogin = QPushButton('Login', self)
        self.buttonLogin.clicked.connect(self.handleLogin)
        self.layout.addWidget(self.buttonLogin)
        
        self.buttonExit = QPushButton('Sair', self)
        self.buttonExit.clicked.connect(self.close)
        self.layout.addWidget(self.buttonExit)

    def handleLogin(self):
        self.loginResponse = self.soapConsumer.service.login({
            'Nome': self.textName.text(), 
            'Senha': self.textPass.text(), 
            'IPaddres' : socket.gethostbyname(socket.getfqdn())
        })
        if self.loginResponse.cdRetorno == 0:
            token = self.loginResponse.token
            self.accept()
        else:
            QMessageBox.warning(self, 'ERRO', self.loginResponse.dsRetorno)
    
    def getLoginResponse(self):
        return self.loginResponse
