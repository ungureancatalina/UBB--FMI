using System;

namespace LFTC_Lab2
{
    public class Meniu
    {
        public static void Afiseaza()
        {
            // L(A)={w∈{0,1,2}+} toate sirurile nevide formate doar din simboluri 0, 1 și 2
            Console.WriteLine("\n --- MENIU ---");
            Console.WriteLine("1. Afiseaza starile");
            Console.WriteLine("2. Afiseaza alfabetul");
            Console.WriteLine("3. Afiseaza tranzitiile");
            Console.WriteLine("4. Afiseaza starile finale");
            Console.WriteLine("5. Verifica o secventa");
            Console.WriteLine("6. Afiseaza cel mai lung prefix acceptat");
            Console.WriteLine("0. Iesire");
            Console.WriteLine("-------------");
        }
    }
}