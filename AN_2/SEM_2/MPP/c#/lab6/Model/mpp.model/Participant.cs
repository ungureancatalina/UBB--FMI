using System.Text.Json.Serialization;
using MessagePack;

namespace Model.mpp.model
{
    [Serializable]
    public class Participant : IDentifiable<int>
    {
        [JsonPropertyName("idParticipant")] 
        public int Id { get; set; }
        public string Nume { get; set; }
        public string Cnp { get; set; }
        public int CapacitateMotor { get; set; }
        public Echipa Echipa { get; set; }

        public Participant() { }

        public Participant(int id, string nume, string cnp, int capacitateMotor, Echipa echipa)
        {
            Id = id;
            Nume = nume;
            Cnp = cnp;
            CapacitateMotor = capacitateMotor;
            Echipa = echipa;
        }

        public Participant(string nume, string cnp, int capacitateMotor, Echipa echipa)
        {
            Nume = nume;
            Cnp = cnp;
            CapacitateMotor = capacitateMotor;
            Echipa = echipa;
        }

        public void SetId(int id) => Id = id;
        public int GetId() => Id;
    }

}
