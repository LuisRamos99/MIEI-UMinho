using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class UtilizadorPedidosAmizade
    {
        public int IdReceive { get; set; }
        public int IdSend { get; set; }

        public virtual Utilizador IdReceiveNavigation { get; set; }
        public virtual PedidosAmizade IdSendNavigation { get; set; }
    }
}
