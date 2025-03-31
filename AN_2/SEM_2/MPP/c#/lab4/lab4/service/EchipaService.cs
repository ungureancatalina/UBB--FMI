using lab4.domain;
using log4net;

namespace lab4.service;

public class EchipaService(IEchipaRepository echipaRepo)
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(EchipaService));

    public Echipa FindEchipaByNume(string nume)
    {
        Logger.Debug("Incep cautarea echipei.");
        return echipaRepo.FindByNume(nume);
    }

    public void Adauga(Echipa echipa)
    {
        Logger.Debug("Adauga cautarea echipei.");
        echipaRepo.Adauga(echipa);
    }
    
}