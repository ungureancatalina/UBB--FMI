using System;
using System.IO;
using System.Linq;
using partea2;

class Program
{
    static readonly Dictionary<int, string> reverseCoduri =
        new Dictionary<int, string>
        {
            {0,"ID"},
            {1,"CONST_NUM"},
            {2,"CONST_SIR"},
            {3,"int"},
            {4,"double"},
            {5,"siruri"},
            {6,"daca"},
            {7,"altfel_daca"},
            {8,"altfel"},
            {9,"atata_timp_cat"},
            {10,"continua_pentru"},
            {11,"citire"},
            {12,"afisare"},
            {13,"("},
            {14,")"},
            {15,"{"},
            {16,"}"},
            {17,";"},
            {18,","},
            {19,"="},
            {20,"+"},
            {21,"-"},
            {22,"*"},
            {23,"/"},
            {24,"%"},
            {25,"=="},
            {26,"!="},
            {27,"<"},
            {28,">"},
            {29,"<="},
            {30,">="},
            {31,"start"},
            {32,"end"}
        };
    static List<string> CitesteFIPcaListaDeTermeni(string path)
    {
        var tokens = new List<string>();
        var lines = File.ReadAllLines(path);

        for (int i = 1; i < lines.Length; i++)
        {
            var parts = lines[i]
                .Split(new[] { ' ', '\t' }, StringSplitOptions.RemoveEmptyEntries);

            int cod = int.Parse(parts[0]);
            tokens.Add(reverseCoduri[cod]);
        }

        return tokens;
    }
    
    static void Main()
    {
        var grammar = Grammar.LoadFromFile("grammar.txt");
        var parser = new SLRParser(grammar);

        var inputTokens = CitesteFIPcaListaDeTermeni("fip.txt");

        Console.WriteLine("Tokeni din FIP:");
        Console.WriteLine(string.Join(" ", inputTokens));
        Console.WriteLine();

        if (parser.Parse(inputTokens, out var trace))
        {
            Console.WriteLine("Program ACCEPTAT sintactic (SLR)");
        }
        else
        {
            Console.WriteLine("EROARE SINTACTICA");
        }

        Console.WriteLine("\nAnaliza:");
        foreach (var t in trace)
            Console.WriteLine(t);
    }
}