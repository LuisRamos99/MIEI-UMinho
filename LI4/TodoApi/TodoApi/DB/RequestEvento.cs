using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class RequestEvento
    {
        public RequestEvento()
        {
            EventoHasRequests = new HashSet<EventoHasRequests>();
        }

        public int IdUserRequest { get; set; }

        public virtual ICollection<EventoHasRequests> EventoHasRequests { get; set; }
    }
}
