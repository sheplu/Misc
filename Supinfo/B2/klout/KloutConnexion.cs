using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;
using System.Text.RegularExpressions;

namespace TpSupinfo
{
    public class KloutConnection
    {
        public KloutConnection()
        {
            
        }

        public Person Principal { get; set; } //Return info of the user here

        public event EventHandler UserInfoDownloaded; //Hire this event when you have finished to download info
        private HttpWebRequest request; //To generate the request

        string name;

        public void BeginGetUserInfo(string username)
        {
            Person principal = new Person();
            name = username;
            //Code HERE with a thread or background worker
            String Url = String.Format(Info.ShowUser, username);
            request = (HttpWebRequest)WebRequest.Create(Url);
            request.BeginGetResponse(new AsyncCallback(LoadUserInfo), null);
        }

        public void LoadUserInfo(IAsyncResult ar)
        {
            //Code here, with JSON deserialization, try/catch, hire of the event…
            HttpWebResponse requete = request.EndGetResponse(ar) as HttpWebResponse;
            StreamReader StreamIn = new StreamReader(requete.GetResponseStream());
            String fileJson = StreamIn.ReadToEnd();

            // le json devient un string et ensuite on decoupe
            char[] delimiterChars = { ' ', ',', '.', ':', '\t' };
            string text = fileJson;
            string[] words = text.Split(delimiterChars);

            int i = 0;
            int j = 0;
            int k = 0;
            foreach (string s in words)
            {
                i++;
                if (s == "\""+name+"\"")
                {
                    j = i+2;
                    k = i+3;
                  
                }
            }
            


            //on teste si ca retourne un nombre, donc si on a matché qqn.
            // thx regex :)
            Regex objNotNumberPattern = new Regex("[0-9]");
            if (objNotNumberPattern.IsMatch(words[j]) && objNotNumberPattern.IsMatch(words[k]))
            {
                System.Console.WriteLine("Le score Klout est de : " + words[j] + "." + words[k]);
            }
            else
            {
                System.Console.WriteLine("La personne que vous cherchez n'existe pas!");
            }

            System.Console.WriteLine("Veuillez appuyer sur une touche pour quitter le programme");

        }
    }
}




   

