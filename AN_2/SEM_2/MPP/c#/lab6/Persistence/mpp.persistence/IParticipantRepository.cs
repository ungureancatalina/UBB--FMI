namespace Persistence.mpp.persistence;
using Model.mpp.model;


public interface IParticipantRepository : IRepositoryInterface<int, Participant>
{
    List<Participant> FindByEchipa(Echipa echipa);
    Participant FindByNume(String nume);
}