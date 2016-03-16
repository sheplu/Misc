using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TpSupinfo
{
    public class Person : AbstractModel
    {
        private String City { get; set; }
        private String Influencedby { get; set; }
        private String Influencerof { get; set; }
        private String Kavatar { get; set; }
        private String Kscore { get; set; }
        private String Realname { get; set; }
        private String Score { get; set; }
        private String Topics { get; set; }
        private String TwitterDescription { get; set; }
        private String TwitterScreenN { get; set; }
    }
}
