namespace Persistence.mpp.persistence;
using Model.mpp.model;


public interface IEchipaRepository : IRepositoryInterface<int, Echipa>
{
    Echipa FindByNume(string nume);
    List<Echipa> FindAllOrderedByNume();
}