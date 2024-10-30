package service;

import domain.Prietenie;
import domain.Utilizator;
import domain.validator.ValidationException;
import repository.InMemoryRepository;
import repository.PrietenieFileRepository;
import repository.UtilizatorFileRepository;


import java.util.Vector;

public class UtilizatorService {
    private UtilizatorFileRepository repo_utilizator;
    private PrietenieFileRepository repo_prietenie;

    public UtilizatorService(UtilizatorFileRepository repo_utilizator, PrietenieFileRepository repo_prietenie) {
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

    public Utilizator removeUtilizator(Long id)
    {
        try {
            Utilizator ut= repo_utilizator.findOne(id).orElseThrow(() ->new ValidationException("Nu exista utilizatorul! "));
            Vector<Long> toRemove = new Vector<>();
            getPrietenii().forEach(prietenie -> {
                if (prietenie.getIdUser1().equals(id) || prietenie.getIdUser2().equals(id)) {
                    toRemove.add(prietenie.getId());
                }
            });
            toRemove.forEach(repo_prietenie::delete);
            Utilizator utilizator=repo_utilizator.delete(id).orElseThrow(() -> new ValidationException("Nu exista utilizatorul! "));
            utilizator.getFriends().forEach(friend -> friend.removePrieten(utilizator));
            return utilizator;
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

    public void refreshPrietenii()
    {
        repo_prietenie.findAll().forEach(prietenie -> {
            Utilizator u1=repo_utilizator.findOne(prietenie.getIdUser1()).orElseThrow(null);
            Utilizator u2=repo_utilizator.findOne(prietenie.getIdUser2()).orElseThrow(null);
            u1.addPrieten(u2);
            u2.addPrieten(u1);
        });
    }

    public void addPrietenie(Prietenie prietenie) {
        Utilizator u1=null;
        Utilizator u2=null;
        try{
            u1 = repo_utilizator.findOne(prietenie.getIdUser1()).orElseThrow(ValidationException::new);
            u2 = repo_utilizator.findOne(prietenie.getIdUser2()).orElseThrow(ValidationException::new);
        }
        catch (ValidationException e)
        {
            System.out.println();
        }
       if(getPrietenii()!=null) {
           getPrietenii().forEach(prieten ->
           {
               if(prieten.getIdUser1().equals(prietenie.getIdUser1()) && prieten.getIdUser2().equals(prietenie.getIdUser2()))
               {
                   throw new ValidationException("Exista deja prietenia!");
               }
           });
            if (repo_utilizator.findOne(prietenie.getIdUser1()).isEmpty() || repo_utilizator.findOne(prietenie.getIdUser2()).isEmpty())
                throw new ValidationException("Nu exista utilizatorul!");
            if (prietenie.getIdUser1().equals(prietenie.getIdUser2()))
                throw new ValidationException("Id-ul nu poate fi acelasi!");
        }
        prietenie.setId(getPrietenieId());
        repo_prietenie.save(prietenie);

        assert u1 != null;
        u1.addPrieten(u2);
        assert u2 != null;
        u2.addPrieten(u1);
    }

    public void removePrietenie(Long id1, Long id2)
    {
        Utilizator u1=null;
        Utilizator u2=null;
        try{
            u1 = repo_utilizator.findOne(id1).orElseThrow(() -> new ValidationException(" Nu xista utilizatorul cu id-ul "+ id1+ "!"));
            u2 = repo_utilizator.findOne(id2).orElseThrow(() -> new ValidationException(" Nu xista utilizatorul cu id-ul "+ id2+ "!"));
        }
        catch (ValidationException e)
        {
            System.out.println();
        }
        final Long[] id = {0L};

        repo_prietenie.findAll().forEach(prietenie -> {
            if ((prietenie.getIdUser1().equals(id1) && prietenie.getIdUser2().equals(id2)) || (prietenie.getIdUser1().equals(id2) && prietenie.getIdUser2().equals(id1))) {
                id[0] = prietenie.getId();
            }
        });

        if (id[0] == 0L) {
            throw new IllegalArgumentException("Nu exista prieteni");
        }
        repo_prietenie.delete(id[0]);
        assert u1 != null;
        u1.removePrieten(u2);
        assert u2 != null;
        u2.removePrieten(u1);
    }
}
