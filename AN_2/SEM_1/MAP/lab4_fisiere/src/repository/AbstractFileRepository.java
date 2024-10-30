package repository;

import domain.Entity;
import domain.validator.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {

    String nume_fisier;
    public AbstractFileRepository(String nume_fisier, Validator<E> validator) {
        super(validator);
        this.nume_fisier = nume_fisier;
        loadData();
    }

    private void loadData()
    {
        try (BufferedReader reader =new BufferedReader(new FileReader(nume_fisier)))
        {
            String line;
            while((line=reader.readLine())!=null)
            {
                List<String> data= Arrays.asList(line.split(";"));
                E enity= extractEntity(data);
                super.save(enity);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void writeToFile(E entity)
    {
        try(BufferedWriter writer= new BufferedWriter(new FileWriter(nume_fisier,true)))
        {
            writer.write(createEntity(entity));
            writer.newLine();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void saveAll() {
        try(BufferedWriter bw= new BufferedWriter(new FileWriter(nume_fisier, false)))
        {
            for(var entity: findAll())
            {
                bw.write(createEntity(entity));
                bw.newLine();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> data);

    protected abstract String createEntity(E entity);

    @Override
    public Optional<E> save(E entity) {
        Optional<E> result = super.save(entity);
        if(result.isEmpty())
            writeToFile(entity);
        return result;
    }

    public Optional<E> delete(ID id) {
        Optional<E> entity= super.delete(id);
        if(entity.isPresent())
            saveAll();
        return entity;
    }

    public Optional<E> update(E entity) {
        Optional<E> result = super.update(entity);
        if(result.isPresent())
            saveAll();
        return Optional.ofNullable(entity);
    }


}
