using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab10.Main.domain;
public enum TipJucator
{
    Rezerva,
    Participant
}

public class JucatorActiv : Entity<int>, IEntity<int>
{
    public int idJucator { get; set; }
    public int idMeci { get; set; }
    public int nrPuncteInscrise { get; set; }
    public TipJucator tip { get; set; }

    public JucatorActiv(int idJucator, int idMeci, int nrPuncteInscrise, TipJucator tip)
    {
        this.idJucator = idJucator;
        this.idMeci = idMeci;
        this.nrPuncteInscrise = nrPuncteInscrise;
        this.tip = tip;
    }
    public int Id { get; set; }
}
