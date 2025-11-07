using System;
using System.Collections.Generic;
using System.IO;

namespace Tema2
{
    public class AutomatFinit
    {
        // Multimea starilor
        public List<string> Stari = new();

        // Alfabetul (lista simbolurilor acceptate)
        public List<char> Alfabet = new();

        // Functia de tranzitie: (stare, simbol) -> stare urmatoare
        public Dictionary<(string, char), string> Tranzitii = new();

        // Starea de start
        public string StareStart = "";

        // Multimea starilor finale
        public HashSet<string> StariFinale = new();

        // Metoda de citire a automatului dintr-un fisier .txt
        public void CitireDinFisier(string path)
        {
            string[] lines = File.ReadAllLines(path);
            foreach (string line in lines)
            {
                string linie = line.Trim();
                if (string.IsNullOrEmpty(linie) || linie.StartsWith("#"))
                    continue;

                string[] parti = linie.Split(' ');

                switch (parti[0])
                {
                    case "states":
                        Stari.AddRange(parti[1].Split(','));
                        break;
                    case "alphabet":
                        foreach (var c in parti[1].Split(','))
                            Alfabet.Add(c[0]);
                        break;
                    case "start":
                        StareStart = parti[1];
                        break;
                    case "final":
                        foreach (var s in parti[1].Split(','))
                            StariFinale.Add(s);
                        break;
                    case "trans":
                        // format: trans q0 a q1
                        if (parti.Length == 4)
                            Tranzitii[(parti[1], parti[2][0])] = parti[3];
                        break;
                }
            }
        }

        // Simularea AFD-ului: verifica daca un cuvant este acceptat
        public bool Accepta(string cuvant)
        {
            string stareCurenta = StareStart;

            foreach (char c in cuvant)
            {
                if (!Alfabet.Contains(c)) return false;
                if (!Tranzitii.ContainsKey((stareCurenta, c))) return false;

                stareCurenta = Tranzitii[(stareCurenta, c)];
            }

            return StariFinale.Contains(stareCurenta);
        }
    }
}