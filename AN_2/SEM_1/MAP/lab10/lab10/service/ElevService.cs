using lab10.Main.domain;
using Repository;
using Repository.database;

namespace lab10.Main.service;

public class ElevService
{
    private IRepository<int, Elev> repository;
    private ElevRepoDb repo;
    public ElevService(IRepository<int, Elev> repository)
    {
        this.repository = repository;
    }
    public async Task<IEnumerable<Elev>> getAll()
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

    public async void addElev(Elev e)
    {
        e.setId(await getNewId());
        repository.SaveAsync(e);
    }

    public Task<Elev?> cauta(string nume, string scoala)
    {
        Task<Elev?> e = repo.FindByNumeAndScoalaAsync(nume, scoala);
        return e;
    }
}