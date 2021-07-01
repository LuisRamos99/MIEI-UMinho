using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class EventoHasRequests
    {
        public int IdEvento { get; set; }
        public int IdUserRequest { get; set; }

        public virtual Evento IdEventoNavigation { get; set; }
        public virtual RequestEvento IdUserRequestNavigation { get; set; }
    }
}
