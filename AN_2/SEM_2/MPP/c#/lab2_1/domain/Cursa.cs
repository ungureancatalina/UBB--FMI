namespace lab2_1.domain;

public class Cursa
{
    public int IdCursa { get; set; }
    public string Nume { get; set; }
    public int CapacitateMinima { get; set; }
    public int CapacitateMaxima { get; set; }

    public Cursa(int id, string nume, int capacitateMinima, int capacitateMaxima)
    {
        IdCursa = id;
        Nume = nume;
        CapacitateMinima = capacitateMinima;
        CapacitateMaxima = capacitateMaxima;
    }

    public override string ToString()
    {
        return $"{Nume} ({CapacitateMinima}cap_motor_min - {CapacitateMaxima}cap_motor_max)";
    }
}