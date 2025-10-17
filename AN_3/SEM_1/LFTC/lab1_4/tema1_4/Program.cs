// cata - analizor lexical cu verificare start/end

using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;

namespace Tema1
{
    public class Program
    {
        // dictionar cu codurile pentru fiecare atom lexical (tabel de coduri)
        static readonly Dictionary<string, int> coduri = new Dictionary<string, int>()
        {
            {"ID",0},{"CONST_NUM",1},{"CONST_SIR",2},
            {"int",3},{"double",4},{"siruri",5},
            {"daca",6},{"altfel_daca",7},{"altfel",8},
            {"atata_timp_cat",9},{"continua_pentru",10},
            {"citire",11},{"afisare",12},
            {"(",13},{")",14},{"{",15},{"}",16},
            {";",17},{",",18},{"=",19},{"+",20},{"-",21},
            {"*",22},{"/",23},{"%",24},{"==",25},{"!=",26},
            {"<",27},{">",28},{"<=",29},{">=",30},
            {"start",31},{"end",32}
        };

        // multime cu toate cuvintele cheie
        static readonly HashSet<string> cuvinteCheie = new HashSet<string>()
        {
            "int","double","siruri","daca","altfel_daca","altfel",
            "atata_timp_cat","continua_pentru","citire","afisare","start","end"
        };

        // multime cu toti operatorii si separatorii
        static readonly HashSet<string> operatoriSeparatoare = new HashSet<string>()
        {
            "(",")","{","}",";",",","=","+","-","*","/","%",
            "==","!=","<",">","<=",">="
        };

        // expresii regulate pentru recunoasterea identificatorilor si constantelor
        static readonly Regex regexID = new Regex(@"^[0-9][0-9A-Za-z_]{0,199}$");
        static readonly Regex regexConstNum = new Regex(@"^[+-]?[0-9]+(\.[0-9]+)?$");
        static readonly Regex regexConstSir = new Regex("^\".*\"$");

        // tabela de simboluri (TS) - ordonata lexicografic
        static SortedSet<string> TS = new SortedSet<string>();

        // dictionar pentru acces rapid Θ(1) la pozitia din TS
        static Dictionary<string, int> TSIndex = new Dictionary<string, int>();

        // forma interna a programului (FIP)
        static List<(int cod, int tsIndex)> FIP = new List<(int, int)>();

        // contorizam aparitiile lui start si end pentru verificare
        static int countStartEnd = 0;

        // functie care adauga un lexem in tabela de simboluri
        static int AdaugaInTS(string lexem)
        {
            if (TSIndex.ContainsKey(lexem))
                return TSIndex[lexem];

            TS.Add(lexem);

            var sortedList = new List<string>(TS);
            TSIndex.Clear();
            for (int i = 0; i < sortedList.Count; i++)
            {
                TSIndex[sortedList[i]] = i + 1;
            }

            return TSIndex[lexem];
        }

        public static void Main()
        {
            string inputPath = "program.txt";
            if (!File.Exists(inputPath))
            {
                Console.WriteLine("Eroare: fisierul program.txt nu exista!");
                return;
            }

            string[] lines = File.ReadAllLines(inputPath);
            int lineNumber = 0;

            foreach (string line in lines)
            {
                lineNumber++;

                // eliminam comentariile
                string text = Regex.Replace(line, @"//.*", "");
                text = text.Replace("\t", " ");

                // expresie pentru separarea tokenilor
                string pattern = @"(\s+|==|!=|<=|>=|[\+\-\*/%(),;{}=])";
                string[] tokens = Regex.Split(text, pattern);

                foreach (string tok in tokens)
                {
                    string t = tok.Trim();
                    if (string.IsNullOrEmpty(t)) continue;

                    if (cuvinteCheie.Contains(t))
                    {
                        FIP.Add((coduri[t], 0));

                        // contorizam aparitiile de start si end
                        if (t == "start" || t =="end") countStartEnd++;
                    }
                    else if (operatoriSeparatoare.Contains(t))
                    {
                        FIP.Add((coduri[t], 0));
                    }
                    else if (regexID.IsMatch(t))
                    {
                        int idx = AdaugaInTS(t);
                        FIP.Add((coduri["ID"], idx));
                    }
                    else if (regexConstNum.IsMatch(t))
                    {
                        int idx = AdaugaInTS(t);
                        FIP.Add((coduri["CONST_NUM"], idx));
                    }
                    else if (regexConstSir.IsMatch(t))
                    {
                        int idx = AdaugaInTS(t);
                        FIP.Add((coduri["CONST_SIR"], idx));
                    }
                    else
                    {
                        Console.WriteLine($"Eroare lexica la linia {lineNumber}: token necunoscut '{t}'");
                    }
                }
            }

            // verificare existenta start si end
            if (countStartEnd == 0)
            {
                Console.WriteLine("Eroare: lipsesc 'start' sau/si 'end' in program.");
            }

            // salvam tabela de simboluri (TS)
            var sortedTS = new List<string>(TS);
            using (StreamWriter sw = new StreamWriter("ts.txt"))
            {
                sw.WriteLine("Idx\tLexem");
                for (int i = 0; i < sortedTS.Count; i++)
                {
                    sw.WriteLine($"{i + 1}\t{sortedTS[i]}");
                }
            }

            // salvam forma interna a programului (FIP)
            using (StreamWriter sw = new StreamWriter("fip.txt"))
            {
                sw.WriteLine("Cod\tTS_idx");
                foreach (var (cod, idx) in FIP)
                {
                    sw.WriteLine($"{cod}\t{idx}");
                }
            }

            Console.WriteLine("Analiza lexica finalizata.");
            Console.WriteLine($"TS: {sortedTS.Count} simboluri | FIP: {FIP.Count} intrari");
        }
    }
}
