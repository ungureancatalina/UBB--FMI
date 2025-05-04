using Model.mpp.model;
using Networking.mpp.networking.rpcprotocol;
public class Request
{
    public RequestType type { get; set; }
    public Utilizator utilizator { get; set; }
    public Echipa echipa { get; set; }
    public ParticipantCursa participantCursa { get; set; }
    public Participant participant { get; set; }
    public Cursa cursa { get; set; }
    public String username { get; set; }
    public String numeEchipa { get; set; }
    public String numeParticipant { get; set; }
    
    public int nrparticipant { get; set; }
    
    public List<ParticipantCursa> participantiCurse { get; set; }
    public List<Cursa> curse { get; set; }
    public List<CursaDTO> curseDTO { get; set; }
    public List<Participant> participanti { get; set; }

    // public override string ToString()
    // {
    //     return string.Format("Request[Type={0}, Utilizator={1}, message={2}, data={3}]",
    //         Type,Utilizator,ParticipantCursa,username);
    // }
  
}