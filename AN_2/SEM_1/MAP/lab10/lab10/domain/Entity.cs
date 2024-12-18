using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.Main.domain;

[Serializable]
public abstract class Entity<ID>
{
    public ID Id { get; set; }

    public void setId(ID id)
    {
        Id = id;
    }

    public ID getId()
    {
        return Id;
    }
}
