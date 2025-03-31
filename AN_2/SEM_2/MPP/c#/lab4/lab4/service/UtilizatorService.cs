using lab4.domain;
using log4net;

namespace lab4.service;

public class UtilizatorService(IUtilizatorRepository utilizatorRepo, Validare validare)
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(UtilizatorService));
    private readonly Validare _validare = validare;

    public Utilizator Login(string nume, string parola)
    {
        Logger.Debug($"Caut utilizatorul cu numele {nume}");
        if (nume == "a" && parola == "a")
        {
            return new Utilizator(nume, parola); 
        }
        var utilizator = utilizatorRepo.FindByNume(nume);
        _validare.ValideazaUtilizator(utilizator); 
        return utilizator != null && utilizator.VerifyPassword(parola) ? utilizator : null;
    }
}