using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class UtilizadorGrupo
    {
        public int IdUtilizador { get; set; }
        public int IdGrupo { get; set; }

        public virtual Grupo IdGrupoNavigation { get; set; }
        public virtual Utilizador IdUtilizadorNavigation { get; set; }
    }
}
