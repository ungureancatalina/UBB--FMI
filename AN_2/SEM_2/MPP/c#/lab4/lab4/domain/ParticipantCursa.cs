namespace lab4.domain;

public class ParticipantCursa(Participant part, Cursa cursa)
{
    private Participant _part = part;
    private Cursa _cursa = cursa;

    public Participant GetParticipant() => _part;
    public void SetParticipant(Participant part) => this._part = part;

    public Cursa GetCursa() => _cursa;
    public void SetCursa(Cursa cursa) => this._cursa = cursa;

    public override bool Equals(object? obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
        var that = (ParticipantCursa)obj;
        return Equals(_part, that._part) && Equals(_cursa, that._cursa);
    }

    public override int GetHashCode() => HashCode.Combine(_part, _cursa);

    public override string ToString()
    {
        return $"ParticipantCursa{{part={_part}, cursa={_cursa}}}";
    }
}