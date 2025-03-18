namespace lab3.repository;
using lab3.domain;

public interface ICursaRepository : IRepositoryInterface<int, Cursa>
{
    List<Cursa> FindByCapacitateMinima(int capacitate);
}