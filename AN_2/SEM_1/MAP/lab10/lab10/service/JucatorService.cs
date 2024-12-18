using lab10.Main.domain;
using Repository;

namespace lab10.Main.service;

public class JucatorService
{
    private IRepository<int, Jucator> repository;
    public JucatorService(IRepository<int, Jucator> repository)
    {
        this.repository = repository;
    }
    public async Task<IEnumerable<Jucator>> getAll()
    {
        return await repository.FindAllAsync();
    }

    public async Task<int> getNewId()
    {
        int id = 0;
        int max = 0;
        foreach (var e in await getAll())
        {
            id = e.getId();
            if (id > max)
                max = id;
        }
        return max + 1;
    }

    public async Task addJucator(Jucator j)
    {
        j.setId(await getNewId());
        repository.SaveAsync(j);
    }

    public async Task<Jucator?> getById(int id)
    {
        return await repository.FindOneAsync(id);
    }
}