using System;
using System.Collections.Generic;
using System.Text;

namespace ChatMobile.Models
{
    
    public class Argumentos
    {
        public string Modo { get; set; }  //(Obrigatorio) Recebe ou conecta a alguem(r = recebe, c = conecta)
        public string Ip { get; set; }   //(Obrigatorio/Opcional) ip do cliente, opcional em caso 'modo = r'
        public int Porta { get; set; }    //(Obrigatorio) porta para conexao
        public bool Debug { get; set; } //(Opcional) modo debug (true ou false), caso nao informado padrao e 'false'
        public int Dh { get; set; }   //(Opcional) tamanho de bits para criptografia DiffieHellman(1024 ou 2048), caso nao informado padrao e '1024', ara maior que o padrao usar Java 8
        public int Aes { get; set; }  //(Opcional) tamanho de bits para criptografia AES(128, 192 ou 256), caso nao informado padrao e '128', para valores maior que padrao precisa substituir o arquivo de autorizacao da JDK
        public string Conexao { get; set; } //(Obrigatorio) tipo da conexão, UDP ou TCP
        public UsuarioInput UsuarioInput { get; set; }
        public Token Token { get; set; }

        public Argumentos()
        {

        }
    }
}
