using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

namespace TpSupinfo
{
    public abstract class AbstractModel : INotifyPropertyChanged
    {
        public void OnPropertyChanged(string property)
        {

            if (PropertyChanged != null)

                PropertyChanged(this, new PropertyChangedEventArgs(property));

        }

        public event PropertyChangedEventHandler PropertyChanged;

    } 
}
