using MessagePack;
using System.ComponentModel;
using System.Text.Json.Serialization;

namespace Model.mpp.model
{
    [Serializable]
    public class ParticipantDTO
    {
        public string Nume { get; set; }
        public int CapacitateMoto { get; set; }
        public string Echipa { get; set; }

        public ParticipantDTO() {}

        public ParticipantDTO(string nume, int capacitateMoto, string echipa)
        {
            Nume = nume;
            CapacitateMoto = capacitateMoto;
            Echipa = echipa;
        }
    }

}