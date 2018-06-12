# -*- coding: utf-8 -*-

from PyQt5.QtWidgets import *
from PyQt5.QtCore import QSize, QRect

class SoapClientUpdateUserDialog(QDialog):
    def __init__(self, soapConsumer, authToken, selectedUser, parent=None):
        super(SoapClientUpdateUserDialog, self).__init__(parent)
        self.soapConsumer = soapConsumer
        self.authToken = authToken
        self.selectedUser = selectedUser
        self.layout = QVBoxLayout(self)
        
        self.textName = QLineEdit(self)
        self.textName.setText(selectedUser.Nome)
        self.layout.addWidget(self.textName)
        
        self.updateUserButton = QPushButton('Salvar alterações', self)
        self.updateUserButton.clicked.connect(self.handleSaveUpdates)
        self.layout.addWidget(self.updateUserButton)
    
    def handleSaveUpdates(self):
        self.updateUserResponse = self.soapConsumer.service.atualizaUsuario({
            'autenticacao' : { 'Token' : self.authToken },
            'usuarioUpdate': {
                'id' : self.selectedUser.id,
                'Nome': self.textName.text()
            }
        })
        if self.updateUserResponse.cdRetorno == 0:
            QMessageBox.information(self, 'Informação', 'Usuário alterado com sucesso!')
            self.accept()
        else:
            QMessageBox.warning(self, 'ERRO', self.updateUserResponse.dsRetorno)
