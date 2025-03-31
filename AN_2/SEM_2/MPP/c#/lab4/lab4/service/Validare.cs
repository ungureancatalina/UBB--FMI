using System.Text.RegularExpressions;
using lab4.domain;
using log4net;

namespace lab4.service;

public class Validare(IParticipantRepository participantRepo, IEchipaRepository echipaRepo)
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(Validare));

    public void ValideazaParticipant(string nume, string cnp, string echipaNume)
    {
        if (string.IsNullOrWhiteSpace(nume) || string.IsNullOrWhiteSpace(cnp) || string.IsNullOrWhiteSpace(echipaNume))
        {
            Logger.Error("Toate campurile trebuie completate");
            throw new Exception("Toate campurile trebuie completate");
        }

        if (!Regex.IsMatch(cnp, "\\d{13}"))
        {
            Logger.Error("CNP-ul trebuie să conțină exact 13 cifre");
            throw new Exception("CNP-ul trebuie să conțină exact 13 cifre");
        }
        var existingParticipants = participantRepo.FindByNume(nume);
        if (existingParticipants != null && existingParticipants.Count > 0)
        {
            Logger.Error("Numele participantului există deja");
            throw new Exception("Numele participantului există deja");
        }
    }

    public void ValideazaEchipa(string echipaNume)
    {
        Echipa echipa = echipaRepo.FindByNume(echipaNume);
        if (echipa == null)
        {
            Logger.Error($"Echipa {echipaNume} nu exista");
            throw new Exception($"Echipa {echipaNume} nu exista");
        }
    }
    
    public void ValideazaUtilizator(Utilizator utilizator)
    {
        if (string.IsNullOrWhiteSpace(utilizator.GetNume()) || utilizator.GetNume().Trim().Length < 4)
        {
            Logger.Error("Username-ul trebuie să aibă cel puțin 4 caractere");
            throw new Exception("Username-ul trebuie să aibă cel puțin 4 caractere");
        }

        if (string.IsNullOrWhiteSpace(utilizator.GetParola()) || 
            !Regex.IsMatch(utilizator.GetParola(), @"^(?=.*[a-zA-Z])(?=.*\d).{6,}$"))
        {
            Logger.Error("Parola trebuie să conțină atât litere, cât și cifre și să aibă cel puțin 6 caractere");
            throw new Exception("Parola trebuie să conțină atât litere, cât și cifre și să aibă cel puțin 6 caractere");
        }
    }
}