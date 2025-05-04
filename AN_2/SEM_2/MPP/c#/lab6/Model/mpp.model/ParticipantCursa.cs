using System.Text.Json.Serialization;
using MessagePack;

namespace Model.mpp.model
{
    [Serializable]
    public class ParticipantCursa
    {
        public Participant Participant { get; set; }
        public Cursa Cursa { get; set; }

        public ParticipantCursa() { }

        public ParticipantCursa(Participant part, Cursa cursa)
        {
            Participant = part;
            Cursa = cursa;
        }
    }

}