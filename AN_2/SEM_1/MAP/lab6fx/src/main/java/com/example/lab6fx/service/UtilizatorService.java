package com.example.lab6fx.service;

import com.example.lab6fx.Observable;
import com.example.lab6fx.Observer;
import com.example.lab6fx.domain.Prietenie;
import com.example.lab6fx.domain.Tuple;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.event.UtilizatorEvent;
import com.example.lab6fx.domain.validator.ValidationException;
import com.example.lab6fx.repository.Page;
import com.example.lab6fx.repository.Pageable;
import com.example.lab6fx.repository.PagingRepository;
import com.example.lab6fx.repository.Repository;
import com.example.lab6fx.repository.database.PrietenieRepositoryDB;
import com.example.lab6fx.repository.database.UtilizatorRepositoryDB;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class UtilizatorService implements Observable<UtilizatorEvent> {
    private final PagingRepository<Long,Utilizator> repo_utilizator;
    private final Repository<Tuple<Long,Long>, Prietenie> repo_prietenie;
    private final List<Observer<UtilizatorEvent>> observers;

    public UtilizatorService(PagingRepository<Long,Utilizator> repo_utilizator, Repository<Tuple<Long,Long>, Prietenie> repo_prietenie) {
        this.repo_utilizator = repo_utilizator;
        this.repo_prietenie = repo_prietenie;
        this.observers = new ArrayList<>();
    }

    public Iterable<Utilizator> getAllUtilizators() throws SQLException
    {
        return repo_utilizator.findAll();
    }

    public Iterable<Prietenie> getPrietenii() throws SQLException
    {
        return repo_prietenie.findAll();
    }

    public Page<Utilizator> getAllUtilizatorsPaged(Pageable pageable) throws SQLException {
        return repo_utilizator.findAll(pageable);
    }

    public Long getUtilizatorId() throws SQLException
    {
        Long id = 0L;
        Long max = 0L;
        for(Utilizator u : getAllUtilizators()){
            id = u.getId();
            if(id > max)
                max = id;
        }

        return max+1;
    }

    public Utilizator getOne(Long id) throws SQLException
    {
        for(Utilizator ut: repo_utilizator.findAll()){
            if(ut.getId().equals(id))
                return ut;
        }
        return null;
    }

    public Utilizator findByFullName(String firstName, String lastName) throws SQLException {
        for (Utilizator utilizator : repo_utilizator.findAll()) {
            if (utilizator.getFirstName().equals(firstName) && utilizator.getLastName().equals(lastName)) {
                return utilizator;
            }
        }
        return null;
    }

    public Utilizator addUtilizator(Utilizator utilizator) throws SQLException
    {
        utilizator.setId(getUtilizatorId());
        repo_utilizator.save(utilizator);
        return utilizator;
    }

    public Utilizator removeUtilizator(Long id)
    {
        try {
            Utilizator ut= repo_utilizator.findOne(id).orElseThrow(() ->new ValidationException("Nu exista utilizatorul "));
            List<Tuple<Long, Long>> toRemove = new ArrayList<>();
            getPrietenii().forEach(p -> {
                if (p.getId().getLeft().equals(id) || p.getId().getRight().equals(id)) {
                    toRemove.add(p.getId());
                }
            });
            toRemove.forEach(repo_prietenie::delete);
            Utilizator utilizator = repo_utilizator.delete(id).orElseThrow(() ->new ValidationException("Nu exista utilizatorul "));
            return utilizator;
        }
        catch (IllegalArgumentException | SQLException e)
        {
            System.out.println("Date invalide");
        }
        return null;
    }

    @Override
    public void addObserver(Observer<UtilizatorEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UtilizatorEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorEvent t) {
        observers.forEach(x -> x.update(t));
    }
}
