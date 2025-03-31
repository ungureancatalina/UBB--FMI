namespace lab4.domain;
using System.ComponentModel;

public class CursaDto(string nume, int capacitateMin, int capacitateMax, int nrParticipanti)
    : INotifyPropertyChanged
{
    private string _nume = nume;
    private int _capacitateMin = capacitateMin;
    private int _capacitateMax = capacitateMax;
    private int _nrParticipanti = nrParticipanti;

    public string Nume
    {
        get => _nume;
        set { _nume = value; OnPropertyChanged(nameof(Nume)); }
    }

    public int CapacitateMin
    {
        get => _capacitateMin;
        set { _capacitateMin = value; OnPropertyChanged(nameof(CapacitateMin)); }
    }

    public int CapacitateMax
    {
        get => _capacitateMax;
        set { _capacitateMax = value; OnPropertyChanged(nameof(CapacitateMax)); }
    }

    public int NrParticipanti
    {
        get => _nrParticipanti;
        set { _nrParticipanti = value; OnPropertyChanged(nameof(NrParticipanti)); }
    }

    public event PropertyChangedEventHandler? PropertyChanged;

    private void OnPropertyChanged(string propertyName)
    {
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }
}
