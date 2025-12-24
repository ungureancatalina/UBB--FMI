using System;

namespace tema3
{
    internal class Program
    {
        static void Main(string[] args)
        {
            string path = "program.txt";
            Analyzer analyzer = new Analyzer();
            analyzer.Analyze(path);
        }
    }
}