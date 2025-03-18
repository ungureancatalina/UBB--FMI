namespace lab3.repository;

public interface IRepositoryInterface<ID, T>
{
    void Adauga(T entity);
    void Sterge(ID id);
    void Modifica(T entity);
    T FindById(ID id);
    List<T> FindAll();
}