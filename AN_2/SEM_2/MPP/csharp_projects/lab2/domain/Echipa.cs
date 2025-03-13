namespace MPP_Csharp.domain;

public class Echipa
{
    public int IdEchipa { get; set; }
    public string Nume { get; set; }

    public Echipa(int id, string nume)
    {
        IdEchipa = id;
        Nume = nume;
    }

    public override string ToString()
    {
        return $"Echipa: {Nume}";
    }
}