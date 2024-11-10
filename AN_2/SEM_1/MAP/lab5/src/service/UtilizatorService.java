package service;

import domain.Prietenie;
import domain.Utilizator;
import domain.validator.ValidationException;
import repository.database.PrietenieRepositoryDB;
import repository.database.UtilizatorRepositoryDB;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UtilizatorService {
    private UtilizatorRepositoryDB repo_utilizator;
    private PrietenieRepositoryDB repo_prietenie;

    public UtilizatorService(UtilizatorRepositoryDB repo_utilizator, PrietenieRepositoryDB repo_prietenie) {
        this.repo_utilizator = repo_utilizator;
        this.repo_prietenie = repo_prietenie;
    }

    public Iterable<Utilizator> getUtilizator()
    {
        return repo_utilizator.findAll();
    }

    public Utilizator findUtilizator(Long id)
    {
        return repo_utilizator.findOne(id).orElseThrow(() -> new ValidationException("Nu exista utilizatorul! "));
    }

    public Long getUtilizatorId()
    {
        Long id= 0L;
        for(Utilizator utilizator : repo_utilizator.findAll())
        {
            id=utilizator.getId();
        }
        id++;
        return id;
    }

    public void addUtilizator(Utilizator utilizator)
    {
        utilizator.setId(getUtilizatorId());
        repo_utilizator.save(utilizator);
    }

    public Iterable<Prietenie> getPrietenii()
    {
        return repo_prietenie.findAll();
    }

    public List<Utilizator> getListFriends(Utilizator ut) {
        List<Utilizator> friends = new ArrayList<>();
        getPrietenii().forEach(prietenie -> {
            if (prietenie.getIdUser1().equals(ut.getId())) {
                friends.add(findUtilizator(prietenie.getIdUser2()));
            } else if (prietenie.getIdUser2().equals(ut.getId())) {
                friends.add(findUtilizator(prietenie.getIdUser1()));
            }
        });
        return friends;
    }

    public Utilizator removeUtilizator(Long id)
    {
        Utilizator ut=null;
        try {
            ut= repo_utilizator.findOne(id).orElseThrow(() ->new ValidationException("Nu exista utilizatorul! "));
            if(ut == null)
                throw new IllegalArgumentException("Nu exista utilizatorul!");
            Vector<Long> toRemove = new Vector<>();
            getPrietenii().forEach(prietenie -> {
                if (prietenie.getIdUser1().equals(id) || prietenie.getIdUser2().equals(id)) {
                    toRemove.add(prietenie.getId());
                }
            });
            toRemove.forEach(repo_prietenie::delete);
            repo_utilizator.delete(id);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Date invalide");
        }
        catch (ValidationException e)
        {
            System.out.println();
        }
        return null;
    }

    public Long getPrietenieId()
    {
        Long id=0L;
        for(Prietenie prietenie : repo_prietenie.findAll())
            id=prietenie.getId();
        id++;
        return id;
    }

    public void addPrietenie(Prietenie prietenie) {
       if(getPrietenii()!=null)
       {
           getPrietenii().forEach(prieten ->
           {
               if(prieten.getIdUser1().equals(prietenie.getIdUser1()) && prieten.getIdUser2().equals(prietenie.getIdUser2()))
                   throw new ValidationException("Exista deja prietenia!");
           });
           if (repo_utilizator.findOne(prietenie.getIdUser1()).isEmpty() || repo_utilizator.findOne(prietenie.getIdUser2()).isEmpty())
               throw new ValidationException("Utilizatorii nu exista!");
           if(prietenie.getIdUser1().equals(prietenie.getIdUser2()))
               throw new ValidationException("Utilizatorii sunt la fel!");
       }
       prietenie.setId(getPrietenieId());
       repo_prietenie.save(prietenie);
    }

    public void removePrietenie(Long id1, Long id2)
    {
        final Long[] id = {0L};
        getPrietenii().forEach(prieten ->
        {
            if((prieten.getIdUser1().equals(id1) && prieten.getIdUser2().equals(id2)) || (prieten.getIdUser1().equals(id2) && prieten.getIdUser2().equals(id1)))
                id[0] =prieten.getId();
        });
        if(id[0] ==0L)
            throw new IllegalArgumentException("Nu exista prietenia!");
        repo_prietenie.delete(id[0]);
    }
}
