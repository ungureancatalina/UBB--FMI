namespace lab4.domain;
using System;
using System.ComponentModel;

public class ParticipantDTO(string nume, int capacitateMoto, string echipa) : INotifyPropertyChanged
{
    private string _nume = nume;
    private int _capacitateMoto = capacitateMoto;
    private string _echipa = echipa;

    public event PropertyChangedEventHandler? PropertyChanged;

    public string Nume
    {
        get => _nume;
        set
        {
            _nume = value;
            OnPropertyChanged(nameof(Nume));
        }
    }

    public int CapacitateMoto
    {
        get => _capacitateMoto;
        set
        {
            _capacitateMoto = value;
            OnPropertyChanged(nameof(CapacitateMoto));
        }
    }

    public string Echipa
    {
        get => _echipa;
        set
        {
            _echipa = value;
            OnPropertyChanged(nameof(Echipa));
        }
    }

    public string GetNumeDto()
    {
        return _nume;
    }

    public int GetCapacitateMotoDto()
    {
        return _capacitateMoto;
    }

    public string GetEchipaDto()
    {
        return _echipa;
    }

    protected void OnPropertyChanged(string propertyName)
    {
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }
}