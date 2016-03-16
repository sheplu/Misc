using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;

namespace TpSupinfo
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("==============================================================================");
            Console.WriteLine("==============================================================================");
            Console.WriteLine("===   ||         ||               00       ||       ||    ============     ===");
            Console.WriteLine("===   ||  //     ||             00  00     ||       ||         ||          ===");
            Console.WriteLine("===   || //      ||           00      00   ||       ||         ||          ===");
            Console.WriteLine("===   ||//       ||           00      00   ||       ||         ||          ===");
            Console.WriteLine("===   || \\      ||           00      00   ||       ||         ||          ===");
            Console.WriteLine("===   ||  \\     ||_______      00  00     ||       ||         ||          ===");
            Console.WriteLine("===   ||   \\    |________        00       ==========          ||          ===");
            Console.WriteLine("==============================================================================");
            Console.WriteLine("==============================================================================");
            Console.WriteLine("\n\n\n");
            Console.WriteLine("Veuillez appuyer sur une touche pour continuer");
            Console.ReadLine();
            Console.Clear();

            Console.WriteLine("Veuillez saisir votre nom de compte twitter pour que Klout puisse récuperer votre score :");
            string saisie = Console.ReadLine();
            Console.WriteLine("L'application tente de récupérer les informations de " +saisie);

            KloutConnection Connection = new KloutConnection();
            Connection.BeginGetUserInfo(saisie);


            Console.ReadLine();




      
        }
    }
}
