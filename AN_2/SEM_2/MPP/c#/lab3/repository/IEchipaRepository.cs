namespace lab3.repository;
using lab3.domain;

public interface IEchipaRepository : IRepositoryInterface<int, Echipa>
{
    Echipa FindByNume(string nume);
    List<Echipa> FindAllOrderedByNume();
}