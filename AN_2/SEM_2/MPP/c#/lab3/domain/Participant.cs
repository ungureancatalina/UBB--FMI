namespace lab3.domain;

public class Participant : Identifiable<int>
{
    private int idParticipant;
    private string nume;
    private string CNP;
    private int capacitateMotor;
    private Echipa echipa;

    public Participant(string nume, string CNP, int capacitateMotor, Echipa echipa)
    {
        this.nume = nume;
        this.CNP = CNP;
        this.capacitateMotor = capacitateMotor;
        this.echipa = echipa;
    }

    public Participant(int idParticipant, string nume, string CNP, int capacitateMotor, Echipa echipa)
    {
        this.idParticipant = idParticipant;
        this.nume = nume;
        this.CNP = CNP;
        this.capacitateMotor = capacitateMotor;
        this.echipa = echipa;
    }

    public void SetId(int id) => this.idParticipant = id;
    public int GetId() => idParticipant;

    public string GetNume() => nume;
    public void SetNume(string nume) => this.nume = nume;

    public string GetCNP() => CNP;
    public void SetCNP(string CNP) => this.CNP = CNP;

    public int GetCapacitateMotor() => capacitateMotor;
    public void SetCapacitateMotor(int capacitateMotor) => this.capacitateMotor = capacitateMotor;

    public Echipa GetEchipa() => echipa;
    public void SetEchipa(Echipa echipa) => this.echipa = echipa;

    public override bool Equals(object? obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
        var participant = (Participant)obj;
        return idParticipant == participant.idParticipant;
    }

    public override int GetHashCode() => idParticipant.GetHashCode();

    public override string ToString()
    {
        return $"Participant{{idParticipant={idParticipant}, nume='{nume}', CNP='{CNP}', capacitateMotor={capacitateMotor}, echipa={echipa}}}";
    }
}