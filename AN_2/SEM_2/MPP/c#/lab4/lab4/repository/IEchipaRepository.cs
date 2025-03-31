namespace lab4.repository;
using lab4.domain;

public interface IEchipaRepository : IRepositoryInterface<int, Echipa>
{
    Echipa FindByNume(string nume);
    List<Echipa> FindAllOrderedByNume();
}