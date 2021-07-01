using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class Utilizador
    {
        public Utilizador()
        {
            AmigosIdUser1Navigation = new HashSet<Amigos>();
            AmigosIdUser2Navigation = new HashSet<Amigos>();
            Evento = new HashSet<Evento>();
            UtilizadorConvites = new HashSet<UtilizadorConvites>();
            UtilizadorEvento = new HashSet<UtilizadorEvento>();
            UtilizadorGrupo = new HashSet<UtilizadorGrupo>();
            UtilizadorOpcao = new HashSet<UtilizadorOpcao>();
            UtilizadorPedidosAmizade = new HashSet<UtilizadorPedidosAmizade>();
        }

        public int Id { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public float? Longitude { get; set; }
        public float? Latitude { get; set; }
        public string UrlFoto { get; set; }
        public string Morada { get; set; }
        public DateTime DataNascimento { get; set; }
        public string Genero { get; set; }
        public string Bio { get; set; }

        public virtual ICollection<Amigos> AmigosIdUser1Navigation { get; set; }
        public virtual ICollection<Amigos> AmigosIdUser2Navigation { get; set; }
        public virtual ICollection<Evento> Evento { get; set; }
        public virtual ICollection<UtilizadorConvites> UtilizadorConvites { get; set; }
        public virtual ICollection<UtilizadorEvento> UtilizadorEvento { get; set; }
        public virtual ICollection<UtilizadorGrupo> UtilizadorGrupo { get; set; }
        public virtual ICollection<UtilizadorOpcao> UtilizadorOpcao { get; set; }
        public virtual ICollection<UtilizadorPedidosAmizade> UtilizadorPedidosAmizade { get; set; }
    }
}
