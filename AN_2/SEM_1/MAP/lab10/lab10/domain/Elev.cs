using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repository;

namespace lab10.Main.domain;
public class Elev : Entity<int>, IEntity<int>
{
    private string nume;
    private string scoala;

    public Elev(string nume, string scoala)
    {
        this.nume = nume;
        this.scoala = scoala;
    }

    public string getNume() => nume;
    public string getScoala() => scoala;
    public int Id { get; set; }
}
