using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class Evento
    {
        public Evento()
        {
            EventoHasRequests = new HashSet<EventoHasRequests>();
            UtilizadorEvento = new HashSet<UtilizadorEvento>();
            Votacao = new HashSet<Votacao>();
        }

        public int Id { get; set; }
        public string Nome { get; set; }
        public DateTime DataHora { get; set; }
        public float Longitude { get; set; }
        public float Latitude { get; set; }
        public int TipoEvento { get; set; }
        public int IdAdmin { get; set; }
        public string Descricao { get; set; }
        public int? IdadeMinima { get; set; }

        public virtual Utilizador IdAdminNavigation { get; set; }
        public virtual ICollection<EventoHasRequests> EventoHasRequests { get; set; }
        public virtual ICollection<UtilizadorEvento> UtilizadorEvento { get; set; }
        public virtual ICollection<Votacao> Votacao { get; set; }
    }
}
