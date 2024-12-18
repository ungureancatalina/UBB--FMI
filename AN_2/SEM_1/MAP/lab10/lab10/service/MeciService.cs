using lab10.Main.domain;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.service;
public class MeciService
{
    private IRepository<int, Meci> repository;

    public MeciService(IRepository<int, Meci> repository)
    {
        this.repository = repository;
    }

    public async Task<IEnumerable<Meci>> getAll()
    {
        return await repository.FindAllAsync();
    }

    public async Task<int> getNewId()
    {
        int maxId = 0;
        var meciuri = await getAll();
        foreach (var meci in meciuri)
        {
            if (meci.Id > maxId)
            {
                maxId = meci.Id;
            }
        }
        return maxId + 1;
    }

    public async Task addMeci(Meci meci)
    {
        meci.Id = await getNewId();
        await repository.SaveAsync(meci);
    }

    public async Task<Meci?> DeleteMeci(int id)
    {
        return await repository.DeleteAsync(id);
    }

    public async Task<Meci?> UpdateMeci(Meci meci)
    {
        return await repository.UpdateAsync(meci);
    }
    public async Task<Meci?> getById(int id)
    {
        return await repository.FindOneAsync(id);
    }

}