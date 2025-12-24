using System;
using System.Linq;
using partea1;

class Program
{
    static void Main()
    {
        var grammar = Grammar.LoadFromFile("grammar.txt");
        var parser = new SLRParser(grammar);

        var ok = 1;
        while(ok == 1)
        {
            Console.Write("Introdu secventa: ");
            var input = Console.ReadLine()
                .Split(" ", StringSplitOptions.RemoveEmptyEntries)
                .ToList();
            if (input[0] == "exit")
            {
                ok = 0;
                break;
            }
            parser.Parse(input, out var trace);

            Console.WriteLine("\nAnaliza:");
            foreach (var t in trace)
                Console.WriteLine(t);
        }
        
    }
}