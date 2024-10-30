package domain.validator;

import domain.Prietenie;
import domain.Utilizator;
import repository.InMemoryRepository;

import java.util.Optional;

public class PrietenieValidator implements Validator<Prietenie>
{
    private InMemoryRepository<Long, Utilizator> repo;

    public PrietenieValidator(InMemoryRepository<Long, Utilizator> repo)
    {
        this.repo = repo;
    }

    @Override
    public void validate(Prietenie obj) throws ValidationException
    {
        Optional<Utilizator> u1=repo.findOne(obj.getIdUser1());
        Optional<Utilizator> u2=repo.findOne(obj.getIdUser2());

        if(obj.getIdUser1() ==null || obj.getIdUser2() == null)
            throw  new ValidationException(("Id-ul nu poate fi null"));
        if(u1 == null || u2 == null)
            throw new ValidationException(("Id-ul nu exista"));
    }
}
