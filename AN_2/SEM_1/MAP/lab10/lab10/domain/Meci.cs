using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.Main.domain;
public class Meci : Entity<int>, IEntity<int>
{
    public Echipa Echipa1 { get; set; }
    public Echipa Echipa2 { get; set; }
    public DateTime Data { get; set; }

    public Meci(Echipa echipa1, Echipa echipa2, DateTime data)
    {
        Echipa1 = echipa1;
        Echipa2 = echipa2;
        Data = data;
    }

    public int Id { get; set; }
}
