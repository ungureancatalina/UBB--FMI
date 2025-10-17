namespace tema1;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;


public class Program
{ 
    // Coduri lexeme
    static Dictionary<string, int> coduri = new Dictionary<string, int>()
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

    static HashSet<string> cuvinteCheie = new HashSet<string>()
    {
        "int","double","siruri","daca","altfel_daca","altfel",
        "atata_timp_cat","continua_pentru","citire","afisare","start","end"
    };

    static HashSet<string> operatoriSeparatoare = new HashSet<string>()
    {
        "(",")","{","}",";",",","=","+","-","*","/","%",
        "==","!=","<",">","<=",">="
    };

    // Regex pentru ID și constante
    static Regex regexID = new Regex(@"^[0-9][0-9A-Za-z_]{0,199}$");
    static Regex regexConstNum = new Regex(@"^[+-]?[0-9]+(\.[0-9]+)?$");
    static Regex regexConstSir = new Regex("^\".*\"$");

    // Tabele TS si FIP
    static List<string> TS = new List<string>();
    static List<(int cod, int tsIndex)> FIP = new List<(int, int)>();

    static int AdaugaInTS(string lexem)
    {
        if (TS.Contains(lexem))
            return TS.IndexOf(lexem) + 1;
        TS.Add(lexem);
        return TS.Count;
    }

    static void Main()
    {
        string text = File.ReadAllText("program.txt");

        // Eliminam comentariile
        text = Regex.Replace(text, @"//.*", "");
        text = text.Replace("\n", " \n "); // pastram newline pentru tokenizare

        // Tokenizare simpla
        string pattern = @"(\s+|==|!=|<=|>=|[\+\-\*/%(),;{}])";
        string[] tokens = Regex.Split(text, pattern);
        List<string> tokensCurate = new List<string>();
        foreach (string t in tokens)
        {
            if (!string.IsNullOrWhiteSpace(t))
                tokensCurate.Add(t);
        }

        // Recunoastere lexeme
        foreach (string tok in tokensCurate)
        {
            if (cuvinteCheie.Contains(tok))
            {
                FIP.Add((coduri[tok], 0));
            }
            else if (operatoriSeparatoare.Contains(tok))
            {
                FIP.Add((coduri[tok], 0));
            }
            else if (regexID.IsMatch(tok))
            {
                int idx = AdaugaInTS(tok);
                FIP.Add((coduri["ID"], idx));
            }
            else if (regexConstNum.IsMatch(tok))
            {
                int idx = AdaugaInTS(tok);
                FIP.Add((coduri["CONST_NUM"], idx));
            }
            else if (regexConstSir.IsMatch(tok))
            {
                int idx = AdaugaInTS(tok);
                FIP.Add((coduri["CONST_SIR"], idx));
            }
            else if (tok == "\n")
            {
                continue;
            }
            else
            {
                Console.WriteLine($"Eroare lexicala: '{tok}'");
            }
        }

        // Scriere TS
        using (StreamWriter sw = new StreamWriter("ts.txt"))
        {
            sw.WriteLine("Idx\tLexem");
            for (int i = 0; i < TS.Count; i++)
            {
                sw.WriteLine($"{i + 1}\t{TS[i]}");
            }
        }

        // Scriere FIP
        using (StreamWriter sw = new StreamWriter("fip.txt"))
        {
            sw.WriteLine("Cod\tTS_idx");
            foreach (var (cod, idx) in FIP)
            {
                sw.WriteLine($"{cod}\t{idx}");
            }
        }
    }
}