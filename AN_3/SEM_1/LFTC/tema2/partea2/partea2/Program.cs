using System;
using System.Collections.Generic;
using System.IO;

namespace Tema2
{
    public class Program
    {
        //  Tabelul de coduri pentru atomi lexicali 
        static readonly Dictionary<string, int> coduri = new()
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

        //  Cuvinte cheie si operatori/separatori
        static readonly HashSet<string> cuvinteCheie = new()
        {
            "int","double","siruri","daca","altfel_daca","altfel",
            "atata_timp_cat","continua_pentru","citire","afisare","start","end"
        };

        static readonly HashSet<string> operatoriSeparatoare = new()
        {
            "(",")","{","}",";",",","=","+","-","*","/","%","==","!=","<",">","<=",">="
        };

        //  Tabela de simboluri (TS) si Forma interna a programului (FIP)
        static SortedSet<string> TS = new();
        static Dictionary<string,int> TSIndex = new();
        static List<(int cod,int tsIndex)> FIP = new();
        static int countStartEnd = 0;

        //  Adauga un element in tabela de simboluri
        static int AdaugaInTS(string lexem)
        {
            if (TSIndex.ContainsKey(lexem)) return TSIndex[lexem];
            TS.Add(lexem);
            var sorted = new List<string>(TS);
            TSIndex.Clear();
            for (int i = 0; i < sorted.Count; i++)
                TSIndex[sorted[i]] = i + 1;
            return TSIndex[lexem];
        }
        
        public static void Main()
        {
            //   AICI SE CREEAZA CELE 3 AUTOMATE FINITE
            //   1) pentru identificatori
            //   2) pentru constante intregi
            //   3) pentru constante reale
            var afdIdent = new AutomatFinit();
            var afdInt = new AutomatFinit();
            var afdReal = new AutomatFinit();

            // Incarcare automatelor din fisiere .txt
            afdIdent.CitireDinFisier("afd_id.txt");
            afdInt.CitireDinFisier("afd_int.txt");
            afdReal.CitireDinFisier("afd_real.txt");

            //  Citim programul sursa 
            string inputPath = "program.txt";
            if (!File.Exists(inputPath))
            {
                Console.WriteLine("Eroare: lipseste program.txt");
                return;
            }

            string text = File.ReadAllText(inputPath);

            //  Analiza caracter cu caracter (fara expresii regulate)
            string token = "";
            for (int i = 0; i < text.Length; i++)
            {
                char c = text[i];

                // Ignoram spatiile
                if (char.IsWhiteSpace(c)) continue;

                // Eliminam comentariile "// ... "
                if (c == '/' && i + 1 < text.Length && text[i + 1] == '/')
                {
                    while (i < text.Length && text[i] != '\n') i++;
                    continue;
                }

                token = "";

                // Operator compus (==, !=, <=, >=)
                if (i + 1 < text.Length && (text.Substring(i, 2) == "==" || text.Substring(i, 2) == "!="
                     || text.Substring(i, 2) == "<=" || text.Substring(i, 2) == ">="))
                {
                    token = text.Substring(i, 2);
                    i++;
                }
                // Operator simplu
                else if (operatoriSeparatoare.Contains(c.ToString()))
                {
                    token = c.ToString();
                }
                // Constanta sir intre ghilimele
                else if (c == '"')
                {
                    token += c;
                    i++;
                    while (i < text.Length && text[i] != '"')
                    {
                        token += text[i];
                        i++;
                    }
                    token += '"';
                }
                // Orice alt tip de token
                else
                {
                    while (i < text.Length && !char.IsWhiteSpace(text[i]) &&
                          !operatoriSeparatoare.Contains(text[i].ToString()))
                    {
                        token += text[i];
                        i++;
                    }
                    i--;
                }

                // Procesam tokenul gasit
                ProceseazaToken(token, afdIdent, afdInt, afdReal);
            }

            //  Verificam prezenta "start" si "end"
            if (countStartEnd == 0)
                Console.WriteLine("Eroare: lipsesc 'start' sau/si 'end'.");

            // Scriem fisierele rezultate (FIP + TS)
            ScrieFisiere();
            Console.WriteLine("Analiza lexica finalizata.");
        }

        //  Functia care decide tipul fiecarui token
        //  (foloseste cele 3 automate finite pentru identificare)
        static void ProceseazaToken(string t, AutomatFinit afdIdent, AutomatFinit afdInt, AutomatFinit afdReal)
        {
            if (string.IsNullOrEmpty(t)) return;

            // Daca este cuvant cheie
            if (cuvinteCheie.Contains(t))
            {
                FIP.Add((coduri[t], 0));
                if (t == "start" || t == "end") countStartEnd++;
            }
            // Daca este operator sau separator
            else if (operatoriSeparatoare.Contains(t))
            {
                FIP.Add((coduri[t], 0));
            }
            // Verificare cu automate finite
            else if (afdIdent.Accepta(t))
            {
                // Token acceptat de automatul pentru identificatori
                FIP.Add((coduri["ID"], AdaugaInTS(t)));
            }
            else if (afdReal.Accepta(t))
            {
                // Token acceptat de automatul pentru numere reale
                FIP.Add((coduri["CONST_NUM"], AdaugaInTS(t)));
            }
            else if (afdInt.Accepta(t))
            {
                // Token acceptat de automatul pentru numere intregi
                FIP.Add((coduri["CONST_NUM"], AdaugaInTS(t)));
            }
            else if (t.StartsWith("\"") && t.EndsWith("\""))
            {
                // Token constant sir
                FIP.Add((coduri["CONST_SIR"], AdaugaInTS(t)));
            }
            else
            {
                // Eroare lexica
                Console.WriteLine($"Eroare lexica: token necunoscut '{t}'");
            }
        }

        //  Scrierea fisierelor de iesire (TS si FIP)
        static void ScrieFisiere()
        {
            var sorted = new List<string>(TS);
            using (StreamWriter sw = new StreamWriter("ts.txt"))
            {
                sw.WriteLine("Idx\tLexem");
                for (int i = 0; i < sorted.Count; i++)
                    sw.WriteLine($"{i + 1}\t{sorted[i]}");
            }

            using (StreamWriter sw = new StreamWriter("fip.txt"))
            {
                sw.WriteLine("Cod\tTS_idx");
                foreach (var (cod, idx) in FIP)
                    sw.WriteLine($"{cod}\t{idx}");
            }
        }
    }
}
