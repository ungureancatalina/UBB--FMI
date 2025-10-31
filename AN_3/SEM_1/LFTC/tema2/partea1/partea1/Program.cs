using System;

namespace LFTC_Lab2
{
    class Program
    {
        static void Main()
        {
            // Creeaza obiectul automatului finit
            var af = new AutomatFinit();

            // Alegerea sursei de citire
            Console.Write("Citire din fisier (1) sau tastatura (2)? ");
            int opt = int.Parse(Console.ReadLine() ?? "0");

            if (opt == 1)
            {
                // Cale fixa catre fisierul automatului
//                string path = "C:/Users/ungur/OneDrive/Desktop/AN_3/SEM1/LFTC/lab/tema2/partea1/partea1/bin/Debug/net9.0/activitate1.txt";
                string path = "C:/Users/ungur/OneDrive/Desktop/AN_3/SEM1/LFTC/lab/tema2/partea1/partea1/bin/Debug/net9.0/afd_literali_int.txt";

                Console.WriteLine($"Se citeste automat din fisierul: {path}");
                af.CitireDinFisier(path);
            }
            else
            {
                // Citire manuala de la tastatura
                af.CitireDeLaTastatura();
            }

            int alegere;
            do
            {
                // Afisare meniu principal
                Meniu.Afiseaza();
                Console.Write("Alege o optiune: ");
                alegere = int.Parse(Console.ReadLine() ?? "0");

                switch (alegere)
                {
                    case 1:
                        // Afiseaza multimea starilor
                        af.AfisareStari();
                        break;

                    case 2:
                        // Afiseaza alfabetul automatului
                        af.AfisareAlfabet();
                        break;

                    case 3:
                        // Afiseaza toate tranzitiile definite
                        af.AfisareTranzitii();
                        break;

                    case 4:
                        // Afiseaza multimea starilor finale
                        af.AfisareFinale();
                        break;

                    case 5:
                        // Verifica daca o secventa este acceptata
                        Console.Write("Introdu secventa: ");
                        string seq = Console.ReadLine() ?? "";
                        Console.WriteLine(af.Accepta(seq)
                            ? "Secventa este ACCEPTATA."
                            : "Secventa NU este acceptata.");
                        break;

                    case 6:
                        // Determina cel mai lung prefix acceptat
                        Console.Write("Introdu secventa: ");
                        string s = Console.ReadLine() ?? "";
                        Console.WriteLine("Cel mai lung prefix acceptat: " + af.CelMaiLungPrefix(s));
                        break;
                }

            } while (alegere != 0);

            Console.WriteLine("Program terminat.");
        }
    }
}
