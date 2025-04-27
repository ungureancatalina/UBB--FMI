using System.Text.Json.Serialization;
using MessagePack;

namespace Model.mpp.model
{
    [Serializable]
    public class Cursa : IDentifiable<int>
    {
        public int Id { get; set; }
        public string Nume { get; set; }
        public int CapacitateMinima { get; set; }
        public int CapacitateMaxima { get; set; }

        public Cursa() { }

        public Cursa(int id, string nume, int capMin, int capMax)
        {
            Id = id;
            Nume = nume;
            CapacitateMinima = capMin;
            CapacitateMaxima = capMax;
        }

        public void SetId(int id) => Id = id;
        public int GetId() => Id;
    }

}