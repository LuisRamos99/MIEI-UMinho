using System;
using System.Collections.Generic;

namespace TodoApi.DB
{
    public partial class Convites
    {
        public Convites()
        {
            UtilizadorConvites = new HashSet<UtilizadorConvites>();
        }

        public int IdConvidador { get; set; }
        public int IdEvento { get; set; }

        public virtual ICollection<UtilizadorConvites> UtilizadorConvites { get; set; }
    }
}
