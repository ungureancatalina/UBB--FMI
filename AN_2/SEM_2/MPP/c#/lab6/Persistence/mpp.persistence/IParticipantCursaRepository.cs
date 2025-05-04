namespace Persistence.mpp.persistence;
using Model.mpp.model;


public interface IParticipantCursaRepository : IRepositoryInterface<int, ParticipantCursa>
{
    List<ParticipantCursa> FindByCursa(Cursa cursa);
    List<ParticipantCursa> FindByParticipant(Participant participant);
    int CountByCursa(Cursa cursa);
    void adaugainbaza(ParticipantCursa participantcursa);
}