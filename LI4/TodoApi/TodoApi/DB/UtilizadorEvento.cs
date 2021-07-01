using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class UtilizadorEvento
    {
        public int IdUtilizador { get; set; }
        public int IdEvento { get; set; }
        public int SharingPosition { get; set; }

        public virtual Evento IdEventoNavigation { get; set; }
        public virtual Utilizador IdUtilizadorNavigation { get; set; }

        public bool IsMember(int id_user, int id_evento)
        {
            if (id_user == IdUtilizador && id_evento == IdEvento) return true;
            else return false;
        }
    }
}
