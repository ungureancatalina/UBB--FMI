namespace lab4.repository;
using lab4.domain;

public interface IParticipantRepository : IRepositoryInterface<int, Participant>
{
    List<ParticipantDTO> FindByEchipa(Echipa echipa);
    List<Participant> FindByNume(String nume);
}