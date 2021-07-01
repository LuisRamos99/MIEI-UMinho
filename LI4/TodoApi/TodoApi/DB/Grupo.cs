using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class Grupo
    {
        public Grupo()
        {
            UtilizadorGrupo = new HashSet<UtilizadorGrupo>();
        }

        public Grupo(Grupo g)
        {
            this.Id = g.Id;
            this.Nome = g.Nome;
            this.UtilizadorGrupo = g.UtilizadorGrupo;
        }

        public int Id { get; set; }
        public string Nome { get; set; }

        public virtual ICollection<UtilizadorGrupo> UtilizadorGrupo { get; set; }
    }
}
