package repository;

import domain.Entity;
import domain.validator.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E>
{

    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public Optional<E> findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("Id-ul nu poate fi null");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("Entity-ul nu poate fi null");
        validator.validate(entity);
        return Optional.ofNullable(entities.put(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if(id==null)
            throw new IllegalArgumentException("Id-ul nu poate fi null");
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("Entity-ul nu poate fi null");
        validator.validate(entity);

        if (entities.get(entity.getId()) == null) {
            return Optional.of(entity);
        }
        entities.put(entity.getId(), entity);
        return Optional.empty();
    }

}
