using MessagePack;
using System;
using System.Security.Cryptography;
using System.Text.Json.Serialization;

namespace Model.mpp.model
{
    [Serializable]
    public class Utilizator : IDentifiable<int>
    {
        public int Id { get; set; }
        public string Nume { get; set; }
        public string Parola { get; set; }

        public Utilizator() {}

        public Utilizator(int id, string nume, string parola)
        {
            Id = id;
            Nume = nume;
            Parola = parola;
        }

        public Utilizator(string nume, string parola)
            : this(0, nume, HashPassword(parola)) {}

        public void SetId(int id) => Id = id;
        public int GetId() => Id;

        public static string HashPassword(string password)
        {
            return BCrypt.Net.BCrypt.HashPassword(password);
        }

        public bool VerifyPassword(string password)
        {
            return BCrypt.Net.BCrypt.Verify(password, Parola);
        }
    }

}