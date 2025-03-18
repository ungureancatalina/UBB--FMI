package ro.mpp.labfx.repository;

import java.util.List;

public interface RepositoryInterface<ID,T> {
    void adauga(T entity);
    void sterge(ID id);
    void modifica(T entity);
    T findById(ID id);
    List<T> findAll();

}