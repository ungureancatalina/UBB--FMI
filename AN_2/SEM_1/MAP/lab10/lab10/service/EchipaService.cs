using lab10.Main.domain;
using Repository;

namespace lab10.Main.service;

public class EchipaService
{
    private IRepository<int, Echipa> repository;
    public EchipaService(IRepository<int, Echipa> repository)
    {
        this.repository = repository;
    }
    public async Task<IEnumerable<Echipa>> getAll()
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

    public async void addEchipa(Echipa e)
    {
        e.setId(await getNewId());
        repository.SaveAsync(e);
    }
}