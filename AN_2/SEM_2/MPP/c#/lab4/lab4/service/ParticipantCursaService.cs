using lab4.domain;
using log4net;

namespace lab4.service;

public class ParticipantCursaService(IParticipantCursaRepository participantCursaRepo)
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(ParticipantCursaService));

    public int FindNrPartByCursa(Cursa cursa)
    {
        Logger.Debug("Incep cautarea participantilor pentru cursa.");
        
        if (cursa == null)
        {
            throw new ArgumentNullException(nameof(cursa), "Cursa cannot be null");
        }
        return participantCursaRepo.CountByCursa(cursa);
    }

    public void adaugainbaza(Participant participant, Cursa cursa)
    {
        Logger.Debug("Incep cautarea participantilor pentru cursa");
        participantCursaRepo.adaugainbaza(participant,cursa);
    }

    public List<ParticipantCursa> FindAll()
    {
        Logger.Debug("Incep cautarea participantilor pentru cursa");
        return participantCursaRepo.FindAll();
    }
}