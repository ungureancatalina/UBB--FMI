namespace lab2_1.domain;

public class ParticipantCursa
{
    public Participant part{ get; set; }
    public Cursa cursa { get; set; }

    public ParticipantCursa(Participant part, Cursa cursa)
    {
        part = part;
        cursa = cursa;
    }

    public override string ToString()
    {
        return $"Participant {part} inscris la cursa {cursa}";
    }
}