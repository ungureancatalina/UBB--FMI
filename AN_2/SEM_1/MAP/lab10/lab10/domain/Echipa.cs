using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repository;

namespace lab10.Main.domain;
public class Echipa : Entity<int>, IEntity<int>
{
    public string nume;

    public Echipa(string n)
    {
        this.nume = n;
    }

    string getNume()
    {
        return this.nume;
    }

    public int Id { get; set; }
}