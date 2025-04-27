namespace Persistence.mpp.persistence;
using Model.mpp.model;

public interface IRepositoryInterface<TId, T>
{
    void Adauga(T entity);
    void Sterge(TId id);
    void Modifica(T entity);
    T FindById(TId id);
    List<T> FindAll();
}