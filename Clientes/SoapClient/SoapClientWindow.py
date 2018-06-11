# -*- coding: utf-8 -*-

from PyQt5.QtWidgets import *
from PyQt5.QtCore import QSize, QRect

from SoapClientCreateUserDialog import *
from SoapClientUpdateUserDialog import *

class SoapClientWindow(QWidget):
    def __init__(self, soapConsumer, wTitle, wWidth, wHeight, token, parent=None):
        super(SoapClientWindow, self).__init__(parent)
        self.authToken = token
        self.soapConsumer = soapConsumer
        self.clientWindowTitle = wTitle 
        self.clientWindowWidth = wWidth
        self.clientWindowHeight = wHeight
        self.initUI()
        self.refreshUsers()
    
    def initUI(self):
        self.resize(QSize(self.clientWindowWidth, self.clientWindowHeight))
        self.setWindowTitle(self.clientWindowTitle)
        self.center()
        self.layout = QVBoxLayout(self)
        
        self.tableWidget = QTableWidget(self)
        self.tableWidget.setColumnCount(3)
        for i, attr in enumerate(['ID', 'Nome', 'Endereço IP']):
            userAttrName = QTableWidgetItem(attr)
            self.tableWidget.setHorizontalHeaderItem(i, userAttrName)
        self.layout.addWidget(self.tableWidget)
        for i, attr in enumerate(['id', 'Nome', 'IPaddres']):
            self.tableWidget.setHorizontalHeaderItem(i, QTableWidgetItem())
        self.tableWidget.setSizeAdjustPolicy(QAbstractScrollArea.AdjustToContents)
        
        self.createUserButton = QPushButton('Criar usuário', self)
        self.createUserButton.clicked.connect(self.createUser)
        self.layout.addWidget(self.createUserButton)
        
        self.updateUserButton = QPushButton('Atualizar usuário', self)
        self.updateUserButton.clicked.connect(self.updateUser)
        self.layout.addWidget(self.updateUserButton)
        
        self.deleteUserButton = QPushButton('Excluir usuário', self)
        self.deleteUserButton.clicked.connect(self.deleteUser)
        self.layout.addWidget(self.deleteUserButton)
        
        self.show()
    
    def center(self):
        qtRectangle = self.frameGeometry()
        centerPoint = QDesktopWidget().availableGeometry().center()
        qtRectangle.moveCenter(centerPoint)
        self.move(qtRectangle.topLeft())
    
    def refreshUsers(self):
        usersListResponse = self.soapConsumer.service.getUsuarios({ 
            'autenticacao': { 'Token': self.authToken } })
        if usersListResponse.cdRetorno == 0:
            self.usersList = usersListResponse.usuariosCollection.usuarios
            self.usersCount = 0
            self.tableWidget.setRowCount(len(self.usersList))
            for i in range(len(self.usersList)):
                for j, attr in enumerate(self.usersList[i]):
                    userAttr = QTableWidgetItem(self.usersList[i][attr])
                    self.tableWidget.setItem(i, j, userAttr)
            self.tableWidget.resizeColumnsToContents()
        else:
            QMessageBox.warning(self, 'ERRO', usersListResponse.dsRetorno)
    
    def createUser(self):
        self.createUserDialog = SoapClientCreateUserDialog(self.soapConsumer)
        self.createUserDialog.exec()
        self.refreshUsers()
    
    def getSelectedUser(self):
        selectedUsersList = list(set(index.row() for index in self.tableWidget.selectedIndexes()))
        if len(selectedUsersList) != 1:
            QMessageBox.warning(self, 'ERRO', 'Selecione exatamente UM usuário para realizar a manutenção!')
            return None
        return self.usersList[selectedUsersList[0]]
    
    def updateUser(self):
        selectedUser = self.getSelectedUser()
        if selectedUser == None:
            return
        self.updateUserDialog = SoapClientUpdateUserDialog(
            self.soapConsumer, 
            self.authToken,
            selectedUser
        )
        self.updateUserDialog.exec()
        self.refreshUsers()
    
    def deleteUser(self):
        selectedUser = self.getSelectedUser()
        if selectedUser == None:
            return
        message = 'Deseja excluir o(a) usuário(a) {}?'.format(selectedUser.Nome)
        buttonReply = QMessageBox.question(self, 'Confirmação', message)
        if buttonReply == QMessageBox.Yes:
            self.deleteUserResponse = self.soapConsumer.service.deletaUsuario({
                'autenticacao' : { 'Token' : self.authToken },
                'usuario': { 'id' : selectedUser.id }
            })
            if self.deleteUserResponse.cdRetorno == 0:
                QMessageBox.information(self, 'Informação', 'Usuário excluído com sucesso!')
            else:
                QMessageBox.warning(self, 'ERRO', self.deleteUserResponse.dsRetorno)
            self.refreshUsers()
            
