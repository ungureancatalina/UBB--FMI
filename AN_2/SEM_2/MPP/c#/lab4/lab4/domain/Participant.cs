namespace lab4.domain;

public class Participant(int idParticipant, string nume, string cnp, int capacitateMotor, Echipa echipa)
    : IDentifiable<int>
{
    private int _idParticipant = idParticipant;
    private string _nume = nume;
    private string _cnp = cnp;
    private int _capacitateMotor = capacitateMotor;
    private Echipa _echipa = echipa;

    public Participant(string nume, string cnp, int capacitateMotor, Echipa echipa) : this(0, nume, cnp, capacitateMotor, echipa) {}

    public void SetId(int id) => this._idParticipant = id;
    public int GetId() => _idParticipant;

    public string GetNume() => _nume;
    public void SetNume(string nume) => this._nume = nume;

    public string GetCnp() => _cnp;
    public void SetCnp(string cnp) => this._cnp = cnp;

    public int GetCapacitateMotor() => _capacitateMotor;
    public void SetCapacitateMotor(int capacitateMotor) => this._capacitateMotor = capacitateMotor;

    public Echipa GetEchipa() => _echipa;
    public void SetEchipa(Echipa echipa) => this._echipa = echipa;

    public override bool Equals(object? obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
        var participant = (Participant)obj;
        return _idParticipant == participant._idParticipant;
    }

    public override int GetHashCode() => _idParticipant.GetHashCode();

    public override string ToString()
    {
        return $"Participant{{idParticipant={_idParticipant}, nume='{_nume}', CNP='{_cnp}', capacitateMotor={_capacitateMotor}, echipa={_echipa}}}";
    }
}