using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class PedidosAmizade
    {
        public PedidosAmizade()
        {
            UtilizadorPedidosAmizade = new HashSet<UtilizadorPedidosAmizade>();
        }

        public int IdUserSend { get; set; }

        public virtual ICollection<UtilizadorPedidosAmizade> UtilizadorPedidosAmizade { get; set; }
    }
}
