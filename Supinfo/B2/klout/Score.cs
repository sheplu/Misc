using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TpSupinfo
{
    public class Score : AbstractModel
    {
        private String AmplificationScore { get; set; }
        private String Delta_1Day { get; set; }
        private String Delta_5Day { get; set; }
        private String Description { get; set; }
        private String Kclass { get; set; }
        private String KclassDescription { get; set; }
        private String Kscore { get; set; }
        private String KscoreDescription { get; set; }
        private String NetworkScore { get; set; }
        private String RealScore { get; set; }
        private String Slope { get; set; }
        private String TrueReach { get; set; }
    }
}
