namespace lab4.repository;
using lab4.domain;

public interface ICursaRepository : IRepositoryInterface<int, Cursa>
{
    List<Cursa> FindByCapacitateMinima(int capacitate);
}