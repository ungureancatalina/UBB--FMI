using MessagePack;
using System.ComponentModel;

namespace Model.mpp.model
{
    [Serializable]
    public class CursaDTO
    {
        public string Nume { get; set; }
        public int CapacitateMin { get; set; }
        public int CapacitateMax { get; set; }
        public int NrParticipanti { get; set; }

        public CursaDTO() {}

        public CursaDTO(string nume, int capacitateMin, int capacitateMax, int nrParticipanti)
        {
            Nume = nume;
            CapacitateMin = capacitateMin;
            CapacitateMax = capacitateMax;
            NrParticipanti = nrParticipanti;
        }
    }
}