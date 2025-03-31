namespace lab4.repository;
using lab4.domain;

public interface IUtilizatorRepository : IRepositoryInterface<int, Utilizator>
{
    Utilizator FindByNume(string nume);
}