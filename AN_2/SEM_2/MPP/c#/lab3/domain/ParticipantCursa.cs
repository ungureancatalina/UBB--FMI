namespace lab3.domain;

public class ParticipantCursa
{
    private Participant part;
    private Cursa cursa;

    public ParticipantCursa(Participant part, Cursa cursa)
    {
        this.part = part;
        this.cursa = cursa;
    }

    public Participant GetParticipant() => part;
    public void SetParticipant(Participant part) => this.part = part;

    public Cursa GetCursa() => cursa;
    public void SetCursa(Cursa cursa) => this.cursa = cursa;

    public override bool Equals(object? obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
        var that = (ParticipantCursa)obj;
        return Equals(part, that.part) && Equals(cursa, that.cursa);
    }

    public override int GetHashCode() => HashCode.Combine(part, cursa);

    public override string ToString()
    {
        return $"ParticipantCursa{{part={part}, cursa={cursa}}}";
    }
}