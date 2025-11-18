using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;

namespace tema3
{
    public class Analyzer
    {
        private static readonly Dictionary<string, TokenType> Keywords = new()
        {
            {"int", TokenType.INT},
            {"double", TokenType.DOUBLE},
            {"siruri", TokenType.SIRURI},
            {"daca", TokenType.DACĂ},
            {"altfel_daca", TokenType.ALTFEL_DACA},
            {"altfel", TokenType.ALTFEL},
            {"atata_timp_cat", TokenType.ATATA_TIMP_CAT},
            {"continua_pentru", TokenType.CONTINUA_PENTRU},
            {"citire", TokenType.CITIRE},
            {"afisare", TokenType.AFISARE},
            {"start", TokenType.START},
            {"end", TokenType.END}
        };

        private static readonly Dictionary<string, TokenType> Operators = new()
        {
            {"(", TokenType.PARANTEZA_DESCHISA},
            {")", TokenType.PARANTEZA_INCHISA},
            {"{", TokenType.ACOLADE_DESCHISA},
            {"}", TokenType.ACOLADE_INCHISA},
            {";", TokenType.PUNCTSI_VIRGULA},
            {",", TokenType.VIRGULA},
            {"=", TokenType.EGAL},
            {"+", TokenType.PLUS},
            {"-", TokenType.MINUS},
            {"*", TokenType.INMULTIRE},
            {"/", TokenType.IMPARTIRE},
            {"%", TokenType.MOD},
            {"==", TokenType.EGAL_EGAL},
            {"!=", TokenType.DIFERIT},
            {"<", TokenType.MAI_MIC},
            {">", TokenType.MAI_MARE},
            {"<=", TokenType.MAI_MIC_EGAL},
            {">=", TokenType.MAI_MARE_EGAL}
        };

        private static readonly Regex RegexID = new(@"^[0-9][0-9A-Za-z_]{0,199}$");
        private static readonly Regex RegexConstNum = new(@"^[+-]?[0-9]+(\.[0-9]+)?$");
        private static readonly Regex RegexConstSir = new("^\".*\"$");

        private readonly SortedSet<string> TS = new();
        private readonly Dictionary<string, int> TSIndex = new();
        private readonly List<(int cod, int tsIndex)> FIP = new();
        private int lineNumber = 0;
        private int startEndCount = 0;

        public void Analyze(string inputPath)
        {
            if (!File.Exists(inputPath))
            {
                Console.WriteLine("Eroare: fisierul program.txt nu exista!");
                return;
            }

            foreach (string line in File.ReadAllLines(inputPath))
            {
                lineNumber++;
                string text = Regex.Replace(line, @"//.*", "").Replace("\t", " ");
                string pattern = @"(\s+|==|!=|<=|>=|[\+\-\*/%(),;{}=])";
                string[] tokens = Regex.Split(text, pattern);

                foreach (string rawTok in tokens)
                {
                    string t = rawTok.Trim();
                    if (string.IsNullOrEmpty(t)) continue;

                    if (Keywords.ContainsKey(t))
                    {
                        FIP.Add(((int)Keywords[t], 0));
                        if (t == "start" || t == "end") startEndCount++;
                    }
                    else if (Operators.ContainsKey(t))
                    {
                        FIP.Add(((int)Operators[t], 0));
                    }
                    else if (RegexID.IsMatch(t))
                    {
                        int idx = AddTS(t);
                        FIP.Add(((int)TokenType.ID, idx));
                    }
                    else if (RegexConstNum.IsMatch(t))
                    {
                        int idx = AddTS(t);
                        FIP.Add(((int)TokenType.CONST_NUM, idx));
                    }
                    else if (RegexConstSir.IsMatch(t))
                    {
                        int idx = AddTS(t);
                        FIP.Add(((int)TokenType.CONST_SIR, idx));
                    }
                    else
                    {
                        Console.WriteLine($"Eroare lexica la linia {lineNumber}: token necunoscut '{t}'");
                    }
                }
            }

            if (startEndCount == 0)
                Console.WriteLine("Eroare: lipsesc 'start' sau/si 'end' in program.");

            SaveTS();
            SaveFIP();

            Console.WriteLine("Analiza lexica finalizata.");
            Console.WriteLine($"TS: {TS.Count} simboluri | FIP: {FIP.Count} intrari");
        }

        private int AddTS(string lexem)
        {
            if (TSIndex.ContainsKey(lexem))
                return TSIndex[lexem];

            TS.Add(lexem);
            var sortedList = new List<string>(TS);
            TSIndex.Clear();
            for (int i = 0; i < sortedList.Count; i++)
                TSIndex[sortedList[i]] = i + 1;
            return TSIndex[lexem];
        }

        private void SaveTS()
        {
            using StreamWriter sw = new("ts.txt");
            sw.WriteLine("Idx\tLexem");
            var sorted = new List<string>(TS);
            for (int i = 0; i < sorted.Count; i++)
                sw.WriteLine($"{i + 1}\t{sorted[i]}");
        }

        private void SaveFIP()
        {
            using StreamWriter sw = new("fip.txt");
            sw.WriteLine("Cod\tTS_idx");
            foreach (var (cod, idx) in FIP)
                sw.WriteLine($"{cod}\t{idx}");
        }
    }
}
