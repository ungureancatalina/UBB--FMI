using lab4.domain;
using log4net;

namespace lab4.service;

public class ParticipantService(IParticipantRepository participantRepo, IEchipaRepository echipaRepo, Validare validare)
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(ParticipantService));

    public void InscriereParticipant(Participant participant)
    { 
        validare.ValideazaParticipant(participant.GetNume(), participant.GetCnp(), participant.GetEchipa().GetNume());
        participantRepo.Adauga(participant);
    }

    public List<Participant> FindAllParticipanti()
    {
        Logger.Debug("Incep cautarea tuturor participantilor.");
        return participantRepo.FindAll();
    }
    
    public List<ParticipantDTO> CautaParticipantiDupaEchipa(string numeEchipa)
    {
        Logger.Debug($"Caut participantii pentru echipa {numeEchipa}");
        validare.ValideazaEchipa(numeEchipa);
        Echipa echipa = echipaRepo.FindByNume(numeEchipa);
        return participantRepo.FindByEchipa(echipa);
    }
}