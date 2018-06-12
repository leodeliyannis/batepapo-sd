# -*- coding: utf-8 -*-

import socket
from PyQt5.QtWidgets import *
from PyQt5.QtCore import QSize, QRect

class SoapClientCreateUserDialog(QDialog):
    def __init__(self, soapConsumer, parent=None):
        super(SoapClientCreateUserDialog, self).__init__(parent)
        self.soapConsumer = soapConsumer
        self.layout = QVBoxLayout(self)
        
        self.textName = QLineEdit(self)
        self.layout.addWidget(self.textName)
        
        self.textPass = QLineEdit(self)
        self.textPass.setEchoMode(QLineEdit.Password)
        self.layout.addWidget(self.textPass)
        
        self.createUserButton = QPushButton('Criar usuário', self)
        self.createUserButton.clicked.connect(self.handleCreateUser)
        self.layout.addWidget(self.createUserButton)
    
    def handleCreateUser(self):
        self.registerResponse = self.soapConsumer.service.criaUsuario({
            'Nome': self.textName.text(), 
            'Senha': self.textPass.text(), 
            'IPaddres' : ''
        })
        if self.registerResponse.cdRetorno == 0:
            QMessageBox.information(self, 'Informação', 'Usuário criado com sucesso!')
            self.accept()
        else:
            QMessageBox.warning(self, 'ERRO', self.registerResponse.dsRetorno)
