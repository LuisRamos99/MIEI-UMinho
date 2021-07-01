using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class Votacao
    {
        public Votacao()
        {
            Opcao = new HashSet<Opcao>();
        }

        public int IdVotacao { get; set; }
        public string Topico { get; set; }
        public int IdEvento { get; set; }

        public virtual Evento IdEventoNavigation { get; set; }
        public virtual ICollection<Opcao> Opcao { get; set; }
    }
}
