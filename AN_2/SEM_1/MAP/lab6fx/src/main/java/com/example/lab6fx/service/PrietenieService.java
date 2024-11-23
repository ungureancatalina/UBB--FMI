package com.example.lab6fx.service;

import com.example.lab6fx.Observable;
import com.example.lab6fx.Observer;
import com.example.lab6fx.domain.Prietenie;
import com.example.lab6fx.domain.Tuple;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.event.UtilizatorEvent;
import com.example.lab6fx.domain.validator.ValidationException;
import com.example.lab6fx.repository.Repository;
import com.example.lab6fx.repository.database.PrietenieRepositoryDB;
import com.example.lab6fx.repository.database.UtilizatorRepositoryDB;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PrietenieService implements Observable<UtilizatorEvent> {
    private final Repository<Long,Utilizator> repo_utilizator;
    private final Repository<Tuple<Long,Long>, Prietenie> repo_prietenie;
    private final List<Observer<UtilizatorEvent>> observers;

    public PrietenieService( Repository<Long,Utilizator> repo_utilizator, Repository<Tuple<Long,Long>, Prietenie> repo_prietenie) {
        this.repo_utilizator = repo_utilizator;
        this.repo_prietenie = repo_prietenie;
        this.observers = new ArrayList<>();
    }

    public Iterable<Prietenie> getAllPrietenii() throws SQLException
    {
        return repo_prietenie.findAll();
    }

    public Prietenie addPrietenie (Prietenie prietenie) throws SQLException {
        Long idUser1 = prietenie.getId().getLeft();
        Utilizator utilizator1 = repo_utilizator.findOne(idUser1).orElseThrow(() -> new ValidationException("Nu exista utilizatorul"));
        Long idUser2 = prietenie.getId().getRight();
        Utilizator utilizator2 = repo_utilizator.findOne(idUser2).orElseThrow(() -> new ValidationException("Nu exista utilizatorul"));
        if(getAllPrietenii() != null){
            getAllPrietenii().forEach(p->{
                if(p.getId().getLeft().equals(idUser1) && p.getId().getRight().equals(idUser2)){
                    throw new ValidationException("The friendship already exist! ");
                }
            });
            if (prietenie.getId().getLeft().equals(prietenie.getId().getRight())) {
                throw new ValidationException("Id-urile nu pot fi aceleasi ");
            }

        }
        repo_prietenie.save(prietenie);
        utilizator1.addPrieten(utilizator2);
        utilizator2.addPrieten(utilizator1);
        return null;
    }

    public Prietenie removePrietenie(Tuple<Long, Long> id)
    {
        Long idUser1 = id.getLeft();
        Utilizator utilizator1 = repo_utilizator.findOne(idUser1).orElseThrow(() -> new ValidationException("Nu exista utilizatorul"));
        Long idUser2 = id.getRight();
        Utilizator utilizator2 = repo_utilizator.findOne(idUser2).orElseThrow(() -> new ValidationException("Nu exista utilizatorul"));
        Prietenie pr = repo_prietenie.findOne(id).orElseThrow(() -> new ValidationException("Nu exista prietenia"));;
        if (pr != null) {
            utilizator1.removePrieten(utilizator2);
            utilizator2.removePrieten(utilizator1);
            repo_prietenie.delete(id);
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
