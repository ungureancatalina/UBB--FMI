using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repository
{
    public interface IRepository<ID, E> where E : IEntity<ID>
    { 
        Task<E?> FindOneAsync(ID id);
        Task<IEnumerable<E?>> FindAllAsync();
        Task<E?> SaveAsync(E entity);
        Task<E?> DeleteAsync(ID id);
        Task<E?> UpdateAsync(E entity);
    }
    public interface IEntity<ID>
    {
        ID Id { get; }
    }
    public class Entity : IEntity<Guid>
    {
        public Guid Id { get; set; }
    }
}
