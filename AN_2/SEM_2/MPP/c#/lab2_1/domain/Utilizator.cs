namespace lab2_1.domain;

public class Utilizator
{
    public int IdUtilizator { get; set; }
    public string Nume { get; set; }
    public string Parola { get; set; }

    public Utilizator(int id, string nume, string parola)
    {
        IdUtilizator = id;
        Nume = nume;
        Parola = parola;
    }

    public override string ToString()
    {
        return $"Utilizator: {Nume}";
    }
}
    

