namespace Persistence.mpp.persistence;
using Model.mpp.model;

public interface ICursaRepository : IRepositoryInterface<int, Cursa>
{
    List<Cursa> FindByCapacitateMinima(int capacitate);
}