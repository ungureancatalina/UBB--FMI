package service;

import domain.Prietenie;
import domain.Utilizator;
import domain.validator.ValidationException;
import repository.memory.InMemoryRepository;


import java.util.Vector;

public class UtilizatorService {
    private InMemoryRepository<Long, Utilizator> repo_utilizator;
    private InMemoryRepository<Long, Prietenie> repo_prietenie;

    public UtilizatorService(InMemoryRepository<Long, Utilizator> repo_utilizator, InMemoryRepository<Long, Prietenie> repo_prietenie) {
        this.repo_utilizator = repo_utilizator;
        this.repo_prietenie = repo_prietenie;
    }

    public Iterable<Utilizator> getUtilizator()
    {
        return repo_utilizator.findAll();
    }

    public Utilizator findUtilizator(Long id)
    {
        return repo_utilizator.findOne(id);
    }

    public Long getUtilizatorId()
    {
        Long id=0L;
        for(Utilizator utilizator : repo_utilizator.findAll())
            id=utilizator.getId();
        id++;
        return id;
    }

    public void addUtilizator(Utilizator utilizator)
    {
        utilizator.setId(getUtilizatorId());
        repo_utilizator.save(utilizator);
    }

    public Utilizator removeUtilizator(Long id)
    {
        try
        {
            Utilizator utilizator = repo_utilizator.findOne(id);
            if(utilizator == null)
                throw new IllegalArgumentException("Nu exista utilizatorul");
            Vector<Long> toRemove = new Vector<>();
            for(Prietenie prietenie : repo_prietenie.findAll())
                if(prietenie.getIdUser2().equals(id) || prietenie.getIdUser1().equals(id))
                    toRemove.add(prietenie.getId());
            for(Long remove: toRemove)
                repo_prietenie.delete(remove);
            Utilizator utilizator1 = repo_utilizator.delete(id);
            for(Utilizator utilizator2 : utilizator1.getFriends())
                utilizator2.removePrieten(utilizator1);
            return utilizator;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Utilizator incorect");
        }
        return null;
    }

    public Iterable<Prietenie> getPrietenii()
    {
        return repo_prietenie.findAll();
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
        Utilizator utilizator1 = repo_utilizator.findOne(prietenie.getIdUser1());
        Utilizator utilizator2 = repo_utilizator.findOne(prietenie.getIdUser2());
        if(getPrietenii()!=null) {
            for (Prietenie p : getPrietenii())
                if (p.getIdUser1().equals(utilizator1) && p.getIdUser2().equals(utilizator2))
                    throw new IllegalArgumentException("Exista deja prietenia");
            if (repo_utilizator.findOne(prietenie.getIdUser1()) == null || repo_utilizator.findOne(prietenie.getIdUser2()) == null)
                throw new ValidationException("Nu exista utilizatorul");
            if (prietenie.getIdUser1().equals(prietenie.getIdUser2()))
                throw new ValidationException("Id-ul nu poate fi acelasi");
        }
        prietenie.setId(getPrietenieId());
        repo_prietenie.save(prietenie);
        utilizator1.addPrieten(utilizator2);
        utilizator2.addPrieten(utilizator1);
    }

    public void removePrietenie(Long id1, Long id2)
    {
        Utilizator utilizator1 = repo_utilizator.findOne(id1);
        Utilizator utilizator2 = repo_utilizator.findOne(id2);
        Long id=0L;
        for(Prietenie prietenie : repo_prietenie.findAll())
            if((prietenie.getIdUser1().equals(id1) && prietenie.getIdUser2().equals(id2))|| (prietenie.getIdUser1().equals(id2) && prietenie.getIdUser2().equals(id1)))
                id=prietenie.getId();
        if(id==0L)
            throw new IllegalArgumentException("Nu exista prieteni");
        repo_prietenie.delete(id);
        utilizator1.removePrieten(utilizator2);
        utilizator2.removePrieten(utilizator1);
    }
}
