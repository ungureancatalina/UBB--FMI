namespace lab3.repository;
using lab3.domain;

public interface IParticipantCursaRepository : IRepositoryInterface<int, ParticipantCursa>
{
    List<ParticipantCursa> FindByCursa(Cursa cursa);
    List<ParticipantCursa> FindByParticipant(Participant participant);
}