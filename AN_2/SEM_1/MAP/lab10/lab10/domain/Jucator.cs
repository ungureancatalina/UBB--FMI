using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace lab10.Main.domain;
public class Jucator : Elev
{
    private Echipa echipa;
    private Elev elev;

    public Jucator(Elev elev, Echipa echipa) : base(elev.getNume(), elev.getScoala())
    {
        this.elev = elev;
        this.echipa = echipa;
    }

    public Echipa getEchipa()
    {
        return echipa;
    }

    public Elev getElev()
    {
        return elev;
    }
    public int Id { get; set; }
}