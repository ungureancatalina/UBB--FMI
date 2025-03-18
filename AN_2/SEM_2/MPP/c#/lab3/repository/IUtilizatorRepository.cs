namespace lab3.repository;
using lab3.domain;

public interface IUtilizatorRepository : IRepositoryInterface<int, Utilizator>
{
    Utilizator FindByNume(string nume);
}