package com.example.lab6fx.repository;
import com.example.lab6fx.domain.Entity;
import com.example.lab6fx.repository.Page;
import com.example.lab6fx.repository.Pageable;

import java.sql.SQLException;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable) throws SQLException;
}
