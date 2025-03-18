namespace lab3.repository;
using lab3.domain;

public interface IParticipantRepository : IRepositoryInterface<int, Participant>
{
    List<Participant> FindByEchipa(Echipa echipa);
}