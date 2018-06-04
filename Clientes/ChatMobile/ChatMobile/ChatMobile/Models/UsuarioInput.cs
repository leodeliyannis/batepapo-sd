using System;
using System.Collections.Generic;
using System.Text;

namespace ChatMobile.Models
{
    public class UsuarioInput
    {
        public string usuario { get; set; }
        public string senha { get; set; }
        public List<string> topicos { get; set; }
    }
}
