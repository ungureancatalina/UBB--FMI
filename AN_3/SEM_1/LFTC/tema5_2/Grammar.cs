using System;
using System.Collections.Generic;
using System.Linq;

public class Grammar
{
    public string StartSymbol;
    public Dictionary<string, List<List<string>>> Productions = new();

    public static Grammar LoadFromFile(string path)
    {
        var g = new Grammar();
        var lines = System.IO.File.ReadAllLines(path);

        foreach (var line in lines)
        {
            if (string.IsNullOrWhiteSpace(line)) continue;

            var parts = line.Split("->");
            var left = parts[0].Trim();
            var rightSide = parts[1].Split("|");

            if (g.StartSymbol == null)
                g.StartSymbol = left;

            if (!g.Productions.ContainsKey(left))
                g.Productions[left] = new List<List<string>>();

            foreach (var r in rightSide)
            {
                var symbols = r.Trim()
                    .Split(" ", StringSplitOptions.RemoveEmptyEntries)
                    .ToList();

                if (symbols.Count == 1 && symbols[0] == "ε")
                    symbols = new List<string>();

                g.Productions[left].Add(symbols);
            }
        }

        return g;
    }

    public bool IsTerminal(string symbol)
    {
        return !Productions.ContainsKey(symbol);
    }

    public IEnumerable<string> NonTerminals()
    {
        return Productions.Keys;
    }

    public IEnumerable<string> Terminals()
    {
        var nts = Productions.Keys.ToHashSet();
        var terms = new HashSet<string>();

        foreach (var prods in Productions.Values)
        foreach (var rhs in prods)
        foreach (var s in rhs)
            if (!nts.Contains(s))
                terms.Add(s);

        terms.Add("$");
        return terms;
    }
}