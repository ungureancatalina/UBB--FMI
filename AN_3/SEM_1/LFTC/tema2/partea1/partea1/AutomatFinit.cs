using System;
using System.Collections.Generic;
using System.IO;

namespace LFTC_Lab2
{
    public class AutomatFinit
    {
        // Lista starilor automatului
        public List<string> Stari { get; } = new();

        // Alfabetul (lista simbolurilor)
        public List<char> Alfabet { get; } = new();

        // Tranzitiile: stare_sursa -> (simbol -> lista_destinatii)
        public Dictionary<string, Dictionary<char, List<string>>> Tranzitii { get; } = new();

        // Starea de start
        public string StareStart { get; private set; } = "";

        // Multimea starilor finale
        public List<string> StariFinale { get; } = new();

        // Flag care indica daca automatul este determinist
        public bool EsteDeterminist { get; private set; } = true;

        // Citire din fisier (automat finit determinist sau nedeterminist)
        public void CitireDinFisier(string path)
        {
            if (!File.Exists(path))
            {
                Console.WriteLine("Eroare: fisierul nu exista!");
                return;
            }

            using var reader = new StreamReader(path);

            // Starile 
            int nrStari = int.Parse(reader.ReadLine() ?? "0");
            var stari = reader.ReadLine()?.Split(' ', StringSplitOptions.RemoveEmptyEntries);
            if (stari != null) Stari.AddRange(stari);

            // Alfabetul 
            int dimAlfabet = int.Parse(reader.ReadLine() ?? "0");
            var simboluri = reader.ReadLine()?.Split(' ', StringSplitOptions.RemoveEmptyEntries);
            if (simboluri != null)
                foreach (var s in simboluri)
                    Alfabet.Add(s[0]);

            // Tranzitiile 
            int nrTranzitii = int.Parse(reader.ReadLine() ?? "0");
            for (int i = 0; i < nrTranzitii; i++)
            {
                var t = reader.ReadLine()?.Split(' ', StringSplitOptions.RemoveEmptyEntries);
                if (t == null || t.Length != 3) continue;

                string sursa = t[0];
                char simbol = t[1][0];
                string destinatie = t[2];

                if (!Tranzitii.ContainsKey(sursa))
                    Tranzitii[sursa] = new Dictionary<char, List<string>>();

                if (!Tranzitii[sursa].ContainsKey(simbol))
                    Tranzitii[sursa][simbol] = new List<string>();

                // Daca deja exista o destinatie pentru acelasi simbol 
                if (Tranzitii[sursa][simbol].Count > 0)
                    EsteDeterminist = false;

                Tranzitii[sursa][simbol].Add(destinatie);
            }

            // Starea de start 
            StareStart = reader.ReadLine()?.Trim() ?? "";

            // Starile finale 
            int nrFinale = int.Parse(reader.ReadLine() ?? "0");
            var finale = reader.ReadLine()?.Split(' ', StringSplitOptions.RemoveEmptyEntries);
            if (finale != null)
                StariFinale.AddRange(finale);

            bool valid = true;

            // Verifica daca starea de start exista
            if (!Stari.Contains(StareStart))
            {
                Console.WriteLine($"Eroare: starea de start '{StareStart}' nu exista in lista starilor!");
                valid = false;
            }

            // Verifica fiecare tranzitie
            foreach (var (sursa, dict) in Tranzitii)
            {
                if (!Stari.Contains(sursa))
                {
                    Console.WriteLine($"Eroare: starea sursa '{sursa}' nu este definita!");
                    valid = false;
                }

                foreach (var (simbol, destinatii) in dict)
                {
                    if (!Alfabet.Contains(simbol))
                    {
                        Console.WriteLine($"Eroare: simbolul '{simbol}' nu face parte din alfabet!");
                        valid = false;
                    }

                    foreach (var dest in destinatii)
                    {
                        if (!Stari.Contains(dest))
                        {
                            Console.WriteLine($"Eroare: starea destinatie '{dest}' nu este definita!");
                            valid = false;
                        }
                    }
                }
            }

            // Verifica daca toate starile finale sunt definite
            foreach (var f in StariFinale)
            {
                if (!Stari.Contains(f))
                {
                    Console.WriteLine($"Eroare: starea finala '{f}' nu exista in lista starilor!");
                    valid = false;
                }
            }

            // Rezultat final al validarii
            if (valid)
                Console.WriteLine("Automat validat cu succes!");
            else
                Console.WriteLine("Au fost gasite erori in fisierul automatului!");
        }

        // Citire de la tastatura
        public void CitireDeLaTastatura()
        {
            Console.Write("Numar stari: ");
            int n = int.Parse(Console.ReadLine() ?? "0");
            Console.WriteLine("Introdu starile:");
            for (int i = 0; i < n; i++) Stari.Add(Console.ReadLine()?.Trim() ?? "");

            Console.Write("Dimensiunea alfabetului: ");
            int m = int.Parse(Console.ReadLine() ?? "0");
            Console.WriteLine("Introdu simbolurile:");
            for (int i = 0; i < m; i++) Alfabet.Add(Console.ReadLine()?[0] ?? ' ');

            Console.Write("Numar tranzitii: ");
            int t = int.Parse(Console.ReadLine() ?? "0");
            Console.WriteLine("Introdu fiecare tranzitie: (stare_curenta simbol stare_urmatoare)");
            for (int i = 0; i < t; i++)
            {
                var input = Console.ReadLine()?.Split(' ', StringSplitOptions.RemoveEmptyEntries);
                if (input == null || input.Length != 3) continue;

                string sursa = input[0];
                char simbol = input[1][0];
                string destinatie = input[2];

                if (!Tranzitii.ContainsKey(sursa))
                    Tranzitii[sursa] = new Dictionary<char, List<string>>();

                if (!Tranzitii[sursa].ContainsKey(simbol))
                    Tranzitii[sursa][simbol] = new List<string>();

                // Daca exista mai multe destinatii -> NFA
                if (Tranzitii[sursa][simbol].Count > 0)
                    EsteDeterminist = false;

                Tranzitii[sursa][simbol].Add(destinatie);
            }

            Console.Write("Stare de start: ");
            StareStart = Console.ReadLine()?.Trim() ?? "";

            Console.Write("Numar stari finale: ");
            int f = int.Parse(Console.ReadLine() ?? "0");
            Console.WriteLine("Introdu starile finale:");
            for (int i = 0; i < f; i++) StariFinale.Add(Console.ReadLine()?.Trim() ?? "");
        }

        // Afisari
        public void AfisareStari() =>
            Console.WriteLine("Starile: " + string.Join(" ", Stari));

        public void AfisareAlfabet() =>
            Console.WriteLine("Alfabetul: " + string.Join(" ", Alfabet));

        public void AfisareTranzitii()
        {
            Console.WriteLine("Tranzitiile:");
            foreach (var s in Tranzitii)
                foreach (var c in s.Value)
                    Console.WriteLine($"({s.Key}, {c.Key}) -> {string.Join(',', c.Value)}");
        }

        public void AfisareFinale() =>
            Console.WriteLine("Starile finale: " + string.Join(" ", StariFinale));

        // Functionalitate: verificare si prefix
        public bool Accepta(string secventa)
        {
            // Functia se aplica doar pentru DFA
            if (!EsteDeterminist)
            {
                Console.WriteLine("Automatul nu este determinist! Functia de acceptare este doar pentru DFA.");
                return false;
            }

            string stareCurenta = StareStart;
            foreach (var ch in secventa)
            {
                // Daca nu exista tranzitie pentru simbol -> respinge
                if (!Tranzitii.ContainsKey(stareCurenta) || !Tranzitii[stareCurenta].ContainsKey(ch))
                    return false;

                stareCurenta = Tranzitii[stareCurenta][ch][0];
            }

            // Accepta daca ultima stare este finala
            return StariFinale.Contains(stareCurenta);
        }

        public string CelMaiLungPrefix(string secventa)
        {
            // Functia se aplica doar pentru DFA
            if (!EsteDeterminist)
            {
                Console.WriteLine("Automatul nu este determinist! Functia prefix este doar pentru DFA.");
                return "";
            }

            string stareCurenta = StareStart;
            string prefix = "";
            string longest = "";

            foreach (var ch in secventa)
            {
                if (!Tranzitii.ContainsKey(stareCurenta) || !Tranzitii[stareCurenta].ContainsKey(ch))
                    break;

                stareCurenta = Tranzitii[stareCurenta][ch][0];
                prefix += ch;

                // Retine prefixul daca s-a ajuns intr-o stare finala
                if (StariFinale.Contains(stareCurenta))
                    longest = prefix;
            }

            return longest;
        }
    }
}
