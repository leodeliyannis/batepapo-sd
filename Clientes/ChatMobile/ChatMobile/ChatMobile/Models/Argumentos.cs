using System;
using System.Collections.Generic;
using System.Text;

namespace ChatMobile.Models
{
    [Serializable]
    public class Argumentos
    {
        public string modo { get; set; }
        public string ip { get; set; }
        public int porta { get; set; }
        public bool debug { get; set; }
        public int dh { get; set; }
        public int aes { get; set; }
        public string conexao { get; set; }
        public UsuarioInput usuarioInput { get; set; }
        public Token token { get; set; }
    }
}
