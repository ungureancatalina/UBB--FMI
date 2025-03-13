package ro.mpp2024.repository;

import java.util.List;

public interface RepositoryInterface<T> {
    void adauga(T entity);
    void sterge(int id);
    void modifica(T entity);
    T findById(int id);
    List<T> findAll();

}