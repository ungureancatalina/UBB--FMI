package repository.file;

import domain.Utilizator;
import domain.validator.Validator;

import java.util.List;

public class UtilizatorFileRepository extends AbstractFileRepository<Long, Utilizator> {

    public UtilizatorFileRepository(String nume_fisier, Validator<Utilizator> validator) {
        super(nume_fisier, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> data) {
        Utilizator utilizator = new Utilizator(data.get(1),data.get(2));
        utilizator.setId(Long.parseLong(data.get(0)));
        return utilizator;
    }

    @Override
    protected String createEntity(Utilizator entity) {
        return entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }
}
