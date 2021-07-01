using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class Amigos
    {
        public int IdUser1 { get; set; }
        public int IdUser2 { get; set; }

        public virtual Utilizador IdUser1Navigation { get; set; }
        public virtual Utilizador IdUser2Navigation { get; set; }
    }
}
