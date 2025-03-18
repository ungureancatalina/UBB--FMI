namespace lab3.domain;
public class Utilizator : Identifiable<int>
{
    private int idUtilizator;
    private string nume;
    private string parola;

    public Utilizator(string nume, string parola)
    {
        this.nume = nume;
        this.parola = parola;
    }

    public Utilizator(int idUtilizator, string nume, string parola)
    {
        this.idUtilizator = idUtilizator;
        this.nume = nume;
        this.parola = parola;
    }

    public void SetId(int id) => this.idUtilizator = id;
    public int GetId() => idUtilizator;

    public string GetNume() => nume;
    public void SetNume(string nume) => this.nume = nume;

    public string GetParola() => parola;
    public void SetParola(string parola) => this.parola = parola;

    public override bool Equals(object? obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
        var utilizator = (Utilizator)obj;
        return idUtilizator == utilizator.idUtilizator;
    }

    public override int GetHashCode() => idUtilizator.GetHashCode();

    public override string ToString()
    {
        return $"Utilizator{{idUtilizator={idUtilizator}, nume='{nume}', parola='{parola}'}}";
    }
}
