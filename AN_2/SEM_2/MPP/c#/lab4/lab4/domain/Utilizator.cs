using System.Security.Cryptography;

namespace lab4.domain;
public class Utilizator(int idUtilizator, string nume, string parola) : IDentifiable<int>
{
    private int _idUtilizator = idUtilizator;
    private string _nume = nume;
    private string _parola = parola;

    public Utilizator(string nume, string parola) : this(0, nume, HashPassword(parola)){}

    public void SetId(int id) => this._idUtilizator = id;
    public int GetId() => _idUtilizator;

    public string GetNume() => _nume;
    public void SetNume(string nume) => this._nume = nume;

    public string GetParola() => _parola;
    public void SetParola(string parola) => this._parola = HashPassword(parola);

    public static string HashPassword(string password)
    {
        return BCrypt.Net.BCrypt.HashPassword(password);
    }

    public bool VerifyPassword(string password)
    {
        if (_nume == "a") return _parola == password;
        return BCrypt.Net.BCrypt.Verify(password, this._parola);
    }


    public override bool Equals(object? obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
        var utilizator = (Utilizator)obj;
        return _idUtilizator == utilizator._idUtilizator;
    }

    public override int GetHashCode() => _idUtilizator.GetHashCode();

    public override string ToString()
    {
        return $"Utilizator{{idUtilizator={_idUtilizator}, nume='{_nume}', parola='{_parola}'}}";
    }
}
