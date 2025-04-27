using MessagePack;
using Model.mpp.model;
using Networking.mpp.networking.rpcprotocol;
using Newtonsoft.Json;

public class Response
{
    public ResponseType type { get; set; }
    public string errorMessage { get; set; }
    public Utilizator utilizator { get; set; }
    public Echipa echipa { get; set; }
    public ParticipantCursa participantCursa { get; set; }
    public Participant participant { get; set; }
    public Cursa cursa { get; set; }
    
    public int nrparticipant { get; set; }
    
    public List<ParticipantCursa> participantiCurse { get; set; }
    public List<Cursa> curse { get; set; }
    public List<CursaDTO> curseDTO { get; set; }
    public List<Participant> participanti { get; set; }

    // public override string ToString()
    // {
    //     return string.Format("Reponse[Type={0},ErrorMessage={1},Utilizator={2}]",
    //         Type,ErrorMessage,Utilizator);
    // }
}