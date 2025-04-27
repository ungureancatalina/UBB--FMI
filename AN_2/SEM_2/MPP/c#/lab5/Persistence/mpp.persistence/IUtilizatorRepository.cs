namespace Persistence.mpp.persistence;
using Model.mpp.model;


public interface IUtilizatorRepository : IRepositoryInterface<int, Utilizator>
{
    Utilizator FindByNume(string nume);
}