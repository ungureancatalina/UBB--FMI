﻿namespace lab2_1.repository;

public interface RepositoryInterface
{
    public interface IRepository<T>
    {
        void adauga(T entity); 
        void sterge(int id);
        void modifica(T entity);
        T findById(int id);
        List<T> findAll();


    }
}