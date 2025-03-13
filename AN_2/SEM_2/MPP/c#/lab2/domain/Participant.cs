namespace MPP_Csharp.domain;

public class Participant
{
    public int IdParticipant { get; set; }
    public string Nume { get; set; }
    public string CNP { get; set; }
    public int CapacitateMotor { get; set; }
    public int? IdEchipa { get; set; } 

    public Participant(int id, string nume, string cnp, int capacitateMotor, int idEchipa)
    {
        IdParticipant = id;
        Nume = nume;
        CNP = cnp;
        CapacitateMotor = capacitateMotor;
        IdEchipa = idEchipa;
    }

    public override string ToString()
    {
        return $"{Nume} - {CapacitateMotor}cap_motor";
    }
}