using System.Text.Json.Serialization;
using MessagePack;

namespace Model.mpp.model
{
    [Serializable]
    public class Echipa : IDentifiable<int>
    {
        [JsonPropertyName("idEchipa")] 
        public int Id { get; set; }
        public string Nume { get; set; }

        public Echipa() {}

        public Echipa(int id, string nume)
        {
            Id = id;
            Nume = nume;
        }

        public Echipa(string nume) : this(0, nume) {}

        public void SetId(int id) => Id = id;
        public int GetId() => Id;
    }

}