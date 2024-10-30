package repository.file;

import domain.Entity;
import domain.validator.Validator;
import repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;

    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader reader= new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while((line=reader.readLine())!=null)
            {
                System.out.println(line);
                List<String> data= Arrays.asList(line.split(";"));
                E entity= extractEntity(data);
                super.save(entity);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity)
    {
        E rez= super.save(entity);
        if(rez==null)
            writeToFile(entity);
        return rez;
    }

    protected void writeToFile(E entity){
        try(BufferedWriter writer= new BufferedWriter(new FileWriter(fileName,true)))
        {
            writer.write(createEntityAsString(entity));
            writer.newLine();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

