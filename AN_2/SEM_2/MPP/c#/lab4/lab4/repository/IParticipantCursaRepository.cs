namespace lab4.repository;
using lab4.domain;

public interface IParticipantCursaRepository : IRepositoryInterface<int, ParticipantCursa>
{
    List<ParticipantCursa> FindByCursa(Cursa cursa);
    List<ParticipantCursa> FindByParticipant(Participant participant);
    int CountByCursa(Cursa cursa);
    
    void adaugainbaza(Participant participant, Cursa cursa);
}