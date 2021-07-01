using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class UtilizadorOpcao
    {
        public int IdUtilizador { get; set; }
        public int IdOpcao { get; set; }
        public int IdVotacao { get; set; }
        public int IdEvento { get; set; }

        public virtual Opcao Id { get; set; }
        public virtual Utilizador IdUtilizadorNavigation { get; set; }
    }
}
