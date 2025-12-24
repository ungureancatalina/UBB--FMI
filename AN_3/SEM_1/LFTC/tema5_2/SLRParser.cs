using System;
using System.Collections.Generic;
using System.Linq;

namespace partea2
{
    public class SLRParser
    {
        private Grammar G;

        public Dictionary<string, HashSet<string>> FIRST = new();
        public Dictionary<string, HashSet<string>> FOLLOW = new();

        public List<Dictionary<string, string>> ACTION = new();
        public List<Dictionary<string, int>> GOTO = new();

        private List<HashSet<Item>> states = new();
        private Dictionary<HashSet<Item>, int> stateIds =
            new(HashSet<Item>.CreateSetComparer());

        public SLRParser(Grammar grammar)
        {
            G = grammar;
            ComputeFIRST();
            ComputeFOLLOW();
            BuildLR0Automaton();
            BuildSLRTable();
        }

        void ComputeFIRST()
        {
            foreach (var nt in G.NonTerminals())
                FIRST[nt] = new HashSet<string>();

            bool changed;
            do
            {
                changed = false;

                foreach (var A in G.NonTerminals())
                foreach (var prod in G.Productions[A])
                {
                    if (prod.Count == 0)
                    {
                        if (FIRST[A].Add("ε")) changed = true;
                        continue;
                    }

                    bool nullable = true;

                    foreach (var X in prod)
                    {
                        if (G.IsTerminal(X))
                        {
                            if (FIRST[A].Add(X)) changed = true;
                            nullable = false;
                            break;
                        }

                        foreach (var t in FIRST[X])
                            if (t != "ε" && FIRST[A].Add(t))
                                changed = true;

                        if (!FIRST[X].Contains("ε"))
                        {
                            nullable = false;
                            break;
                        }
                    }

                    if (nullable)
                        if (FIRST[A].Add("ε")) changed = true;
                }

            } while (changed);
        }

        void ComputeFOLLOW()
        {
            foreach (var nt in G.NonTerminals())
                FOLLOW[nt] = new HashSet<string>();

            FOLLOW[G.StartSymbol].Add("$");

            bool changed;
            do
            {
                changed = false;

                foreach (var A in G.NonTerminals())
                foreach (var prod in G.Productions[A])
                    for (int i = 0; i < prod.Count; i++)
                    {
                        var B = prod[i];
                        if (!G.Productions.ContainsKey(B)) continue;

                        bool epsilonSuffix = true;

                        for (int j = i + 1; j < prod.Count; j++)
                        {
                            var beta = prod[j];

                            if (G.IsTerminal(beta))
                            {
                                if (FOLLOW[B].Add(beta))
                                    changed = true;

                                epsilonSuffix = false;
                                break;
                            }

                            foreach (var t in FIRST[beta])
                                if (t != "ε" && FOLLOW[B].Add(t))
                                    changed = true;

                            if (!FIRST[beta].Contains("ε"))
                            {
                                epsilonSuffix = false;
                                break;
                            }
                        }

                        if (epsilonSuffix)
                            foreach (var t in FOLLOW[A])
                                if (FOLLOW[B].Add(t))
                                    changed = true;
                    }

            } while (changed);
        }

        
        class Item
        {
            public string LHS;
            public List<string> RHS;
            public int Dot;

            public Item(string lhs, List<string> rhs, int dot)
            {
                LHS = lhs;
                RHS = rhs;
                Dot = dot;
            }

            public bool IsComplete => Dot == RHS.Count;
            public string SymbolAfterDot => Dot < RHS.Count ? RHS[Dot] : null;

            public override bool Equals(object obj)
            {
                var o = obj as Item;
                return o != null && LHS == o.LHS &&
                       Dot == o.Dot && RHS.SequenceEqual(o.RHS);
            }

            public override int GetHashCode()
            {
                int h = LHS.GetHashCode() ^ Dot;
                foreach (var s in RHS) h ^= s.GetHashCode();
                return h;
            }
        }

        HashSet<Item> Closure(HashSet<Item> I)
        {
            bool changed;
            do
            {
                changed = false;

                foreach (var item in I.ToList())
                {
                    var B = item.SymbolAfterDot;
                    if (B == null || !G.Productions.ContainsKey(B)) continue;

                    foreach (var prod in G.Productions[B])
                    {
                        var it = new Item(B, prod, 0);
                        if (!I.Contains(it))
                        {
                            I.Add(it);
                            changed = true;
                        }
                    }
                }
            } while (changed);

            return I;
        }

        HashSet<Item> Goto(HashSet<Item> I, string X)
        {
            var J = new HashSet<Item>();

            foreach (var it in I)
                if (it.SymbolAfterDot == X)
                    J.Add(new Item(it.LHS, it.RHS, it.Dot + 1));

            return Closure(J);
        }

        void BuildLR0Automaton()
        {
            var S0 = G.StartSymbol + "'";
            G.Productions[S0] = new List<List<string>>
            {
                new() { G.StartSymbol }
            };

            var start = Closure(new HashSet<Item>
            {
                new Item(S0, new(){G.StartSymbol}, 0)
            });

            states.Add(start);
            stateIds[start] = 0;

            var q = new Queue<HashSet<Item>>();
            q.Enqueue(start);

            while (q.Count > 0)
            {
                var I = q.Dequeue();
                int id = stateIds[I];

                ACTION.Add(new());
                GOTO.Add(new());

                foreach (var X in G.Terminals().Union(G.NonTerminals()))
                {
                    var J = Goto(I, X);
                    if (J.Count == 0) continue;

                    if (!stateIds.ContainsKey(J))
                    {
                        stateIds[J] = states.Count;
                        states.Add(J);
                        q.Enqueue(J);
                    }

                    int to = stateIds[J];

                    if (G.IsTerminal(X))
                        ACTION[id][X] = "s" + to;
                    else
                        GOTO[id][X] = to;
                }
            }
        }

        void BuildSLRTable()
        {
            for (int i = 0; i < states.Count; i++)
            {
                foreach (var it in states[i])
                {
                    if (!it.IsComplete) continue;

                    if (it.LHS == G.StartSymbol + "'")
                    {
                        ACTION[i]["$"] = "acc";
                        continue;
                    }

                    foreach (var a in FOLLOW[it.LHS])
                    {
                        if (ACTION[i].ContainsKey(a))
                            continue;

                        var rhs = string.Join(" ", it.RHS);
                        ACTION[i][a] = $"r({it.LHS}->{rhs})";
                    }
                }
            }
        }


        public bool Parse(List<string> input, out List<string> trace)
        {
            trace = new();
            input.Add("$");

            var st = new Stack<int>();
            st.Push(0);
            int idx = 0;

            while (true)
            {
                int s = st.Peek();
                string a = input[idx];

                if (!ACTION[s].ContainsKey(a))
                {
                    trace.Add($"Eroare la (stare={s}, simbol={a})");
                    return false;
                }

                var act = ACTION[s][a];
                trace.Add($"({s}, {a}) => {act}");

                if (act.StartsWith("s"))
                {
                    st.Push(int.Parse(act[1..]));
                    idx++;
                }
                else if (act.StartsWith("r"))
                {
                    var p = act[2..^1].Split("->");
                    var rhs = p[1].Split(" ", StringSplitOptions.RemoveEmptyEntries);
                    for (int i = 0; i < rhs.Length; i++) st.Pop();
                    st.Push(GOTO[st.Peek()][p[0]]);
                }
                else
                {
                    trace.Add("Acceptat");
                    return true;
                }
            }
        }
    }
}
