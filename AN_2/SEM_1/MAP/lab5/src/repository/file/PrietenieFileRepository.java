package repository.file;

import domain.Prietenie;
import domain.validator.Validator;

import java.util.List;

public class PrietenieFileRepository extends AbstractFileRepository<Long, Prietenie> {
    public PrietenieFileRepository(String nume_fisier, Validator<Prietenie> validator) {
        super(nume_fisier, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> data) {
        Long id1=Long.parseLong(data.get(1));
        Long id2=Long.parseLong(data.get(2));
        Prietenie prietenie=new Prietenie(id1,id2);
        prietenie.setId(Long.parseLong(data.get(0)));
        return prietenie;
    }

    @Override
    protected String createEntity(Prietenie entity) {
        return entity.getId() + ";"+ entity.getIdUser1()+ ";"+ entity.getIdUser2();
    }
}
