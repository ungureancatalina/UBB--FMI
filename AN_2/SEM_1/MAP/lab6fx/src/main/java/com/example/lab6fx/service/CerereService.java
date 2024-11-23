package com.example.lab6fx.service;

import com.example.lab6fx.Observable;
import com.example.lab6fx.Observer;
import com.example.lab6fx.domain.Cerere;
import com.example.lab6fx.domain.Tuple;
import com.example.lab6fx.event.UtilizatorEvent;
import com.example.lab6fx.domain.validator.ValidationException;
import com.example.lab6fx.repository.Repository;
import com.example.lab6fx.repository.database.CerereRepositoryDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CerereService implements Observable<UtilizatorEvent> {
    private final Repository<Long, Cerere> repo_cerere;
    private final List<Observer<UtilizatorEvent>> observers;

    public CerereService(Repository<Long, Cerere> repo_cerere) {
        this.repo_cerere = repo_cerere;
        observers = new ArrayList<>();
    }

    public Iterable<Cerere> getAllCereri() throws SQLException {
        return repo_cerere.findAll();
    }


    public Long getCerereId() throws SQLException
    {
        Long id = 0L;
        Long max = 0L;
        for(Cerere u : getAllCereri()){
            id = u.getId();
            if(id > max)
                max = id;
        }

        return max+1;
    }

    public Cerere getOne(Long id)  throws SQLException
    {
        for(Cerere c: repo_cerere.findAll()){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

    public Cerere addCerere(Cerere cerere) throws SQLException
    {
        cerere.setId(getCerereId());
        repo_cerere.save(cerere);
        return cerere;
    }

    public Cerere removeCerere(Long id)
    {
        try {
            Cerere c = repo_cerere.findOne(id).orElseThrow(() -> new ValidationException("Nu exista cererea"));;
            List<Tuple<Long, Long>> toRemove = new ArrayList<>();


            Cerere c1 = repo_cerere.delete(id).orElseThrow(() -> new ValidationException("Nu exista cererea"));
            return c1;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Utilizator invalid ");
        }
        return null;
    }

    public void updateCerere(Cerere c) throws SQLException{
        repo_cerere.update(c);
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
