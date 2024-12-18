using lab10.Main.domain;
using Repository;
using Repository.database;
namespace lab10.Main.service;

public class JucatorActivService
{
    private IRepository<int, JucatorActiv> repository;

    public JucatorActivService(IRepository<int, JucatorActiv> repository)
    {
        this.repository = repository;
    }

    public async Task<IEnumerable<JucatorActiv>> getAll()
    {
        return await repository.FindAllAsync();
    }

    public async Task<int> getNewId()
    {
        int maxId = 0;
        foreach (var item in await getAll())
        {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }
    public async Task addJucatorActiv(JucatorActiv jucatorActiv)
    {
        await repository.SaveAsync(jucatorActiv);
    }

    public async Task<JucatorActiv> getById(int id)
    {
        var jucatorActiv = await repository.FindOneAsync(id);
        return jucatorActiv;
    }
}