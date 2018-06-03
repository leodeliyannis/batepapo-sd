using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ChatMobile
{

    public class MDPageMenuItem
    {
        public MDPageMenuItem(Type type)
        {
            TargetType = type;
        }
        public int Id { get; set; }
        public string Title { get; set; }

        public Type TargetType { get; set; }
    }
}