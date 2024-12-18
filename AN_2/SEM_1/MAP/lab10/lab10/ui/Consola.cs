using lab10.Main.domain;
using lab10.Main.service;
using lab10.service;

namespace lab10.Main.ui;

public class Consola
{
    private EchipaService srvEchipa;
    private ElevService srvElev;
    private JucatorService srvJucator;
    private MeciService srvMeci;
    private JucatorActivService srvJucatorActiv;

    public Consola(EchipaService srvEchipa, ElevService srvElev, JucatorService srvJucator, MeciService srvMeci, JucatorActivService srvJucatorActiv)
    {
        this.srvEchipa = srvEchipa;
        this.srvElev = srvElev;
        this.srvJucator = srvJucator;
        this.srvMeci = srvMeci;
        this.srvJucatorActiv = srvJucatorActiv;
    }

    public void meniu()
    {
        Console.WriteLine("1. Adauga o echipa");
        Console.WriteLine("2. Adauga un elev");
        Console.WriteLine("3. Adauga un jucator intr-o echipa");
        Console.WriteLine("4. Adauga un meci");
        Console.WriteLine("5. Adauga un jucator activ");
        Console.WriteLine("6. Afiseaza toate echipele");
        Console.WriteLine("7. Afiseaza toti elevii");
        Console.WriteLine("8. Afiseaza toti jucatorii");
        Console.WriteLine("9. Afiseaza toate meciurile");
        Console.WriteLine("10. Afiseaza toti jucatorii activi");
        Console.WriteLine("11. Sa se afiseze toti jucatorii unei echipe data");
        Console.WriteLine("12. Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci");
        Console.WriteLine("13. Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica");
        Console.WriteLine("14. Sa se determine si sa se afiseze scorul de la un anumit meci");
        Console.WriteLine("15. Exit");
    }

    void addEchipa()
    {
        Console.WriteLine("Numele echipei: ");
        string nume = Console.ReadLine();
        srvEchipa.addEchipa(new Echipa(nume));
    }

    void addElev()
    {
        Console.WriteLine("Numele elevului: ");
        string nume = Console.ReadLine();
        Console.WriteLine("Scoala elevului: ");
        string scoala = Console.ReadLine();
        srvElev.addElev(new Elev(nume, scoala));
    }


    async Task addJucator()
    {
        try
        {
            int idEchipa = 0;
            int idElev = 0;

            Console.WriteLine("Elevii: ");
            var elevi = await srvElev.getAll();
            foreach (var el in elevi)
            {
                Console.WriteLine($"{el.getNume()} {el.getScoala()}");
            }

            Console.Write("\n");
            Console.WriteLine("Numele elevului: ");
            string nume = Console.ReadLine();
            Console.WriteLine("Scoala elevului: ");
            string scoala = Console.ReadLine();

            Console.Write("\n");
            var elev = elevi.FirstOrDefault(e => e.getNume() == nume && e.getScoala() == scoala);
            if (elev == null)
            {
                Console.WriteLine("Elevul nu exista");
                return;
            }
            idElev = elev.getId();
            Console.WriteLine("Echipele: ");
            var echipe = await srvEchipa.getAll();
            if (echipe == null || !echipe.Any())
            {
                Console.WriteLine("Nu exista echipe");
                return;
            }
            foreach (var e in echipe)
            {
                Console.WriteLine(e.nume);
            }

            Console.Write("\n");
            Console.WriteLine("Numele echipei: ");
            string numeEchipa = Console.ReadLine();
            var echipa = echipe.FirstOrDefault(e => e.nume == numeEchipa);
            if (echipa == null)
            {
                Console.WriteLine("Echipa nu exista");
                return;
            }
            idEchipa = echipa.getId();
            Elev elv = new Elev(nume, scoala);
            elv.setId(idElev);

            Echipa ec = new Echipa(numeEchipa);
            ec.setId(idEchipa);

            Jucator j = new Jucator(elv, ec);


            await srvJucator.addJucator(j);

        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
    }

    async Task addJucatorActiv()
    {
        try
        {
            Console.WriteLine("Nnumele jucatorului activ: ");
            string numeJucator = Console.ReadLine();
            Console.WriteLine("ID-ul meciului: ");
            int idMeci = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Nr de puncte inscrise de jucator: ");
            int nrPuncteInscrise = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Tipul jucatorului (rezerva sau participant): ");
            string tipInput = Console.ReadLine()?.Trim().ToLower();
            TipJucator tip;
            if (tipInput == "rezerva")
            {
                tip = TipJucator.Rezerva;
            }
            else if (tipInput == "participant")
            {
                tip = TipJucator.Participant;
            }
            else
            {
                Console.WriteLine("Tipul specificat este invalid. Introduceti 'rezerva' sau 'participant'.");
                return;
            }

            var jucator = (await srvJucator.getAll()).FirstOrDefault(j => j.getNume().Equals(numeJucator, StringComparison.OrdinalIgnoreCase));
            if (jucator == null)
            {
                Console.WriteLine("Jucatorul nu exista");
                return;
            }

            var meci = await srvMeci.getById(idMeci);
            if (meci == null)
            {
                Console.WriteLine("Meciul nu exista");
                return;
            }

            JucatorActiv jucatorActiv = new JucatorActiv(jucator.getId(), idMeci, nrPuncteInscrise, tip);

            await srvJucatorActiv.addJucatorActiv(jucatorActiv);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
    }


    async Task addMeci()
    {
        try
        {
            Console.WriteLine("Numele primei echipe: ");
            string echipa1 = Console.ReadLine();
            Console.WriteLine("Numele celei de-a doua echipe: ");
            string echipa2 = Console.ReadLine();
            Console.WriteLine("Data si ora meciului (format: YYYY-MM-DD HH:mm): ");
            DateTime data = DateTime.Parse(Console.ReadLine());
            Console.Write("\n");

            var echipe = await srvEchipa.getAll();
            var echipa1Obj = echipe.FirstOrDefault(e => e.nume == echipa1);
            var echipa2Obj = echipe.FirstOrDefault(e => e.nume == echipa2);

            if (echipa1Obj == null || echipa2Obj == null)
            {
                Console.WriteLine("Una dintre echipe nu exista");
                return;
            }

            Meci meciNou = new Meci(echipa1Obj, echipa2Obj, data);
            await srvMeci.addMeci(meciNou);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
    }

    async Task afisareechipe()
    {
        foreach (var e in await srvEchipa.getAll())
        {
            Console.WriteLine(e.nume);
        }
        Console.Write("\n");
    }

    async Task afisareelevi()
    {
        foreach (var e in await srvElev.getAll())
        {
            Console.WriteLine(e.getNume() + " " + e.getScoala());
        }
        Console.Write("\n");
    }

    async Task afisarejucatori()
    {
        foreach (var jucator in await srvJucator.getAll())
        {
            Console.WriteLine(jucator.getNume() + " " + jucator.getEchipa().nume);
        }
        Console.Write("\n");
    }

    async Task afisaremeciuri()
    {
        foreach (var meci in await srvMeci.getAll())
        {
            Console.WriteLine(meci.Echipa1.nume + " vs " + meci.Echipa2.nume + " " + meci.Data);
        }
        Console.Write("\n");
    }

    async Task afisarejucatoriactivii()
    {
        try
        {
            var jucatoriActivi = await srvJucatorActiv.getAll();
            foreach (var jucatorActiv in jucatoriActivi)
            {
                var jucator = await srvJucator.getById(jucatorActiv.idJucator);
                var meci = await srvMeci.getById(jucatorActiv.idMeci);
                Console.WriteLine(jucator.getNume() + " " + meci.Echipa1.nume + " vs " + meci.Echipa2.nume + " " + "Puncte:" + " " + jucatorActiv.nrPuncteInscrise + " " + jucatorActiv.tip);
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
        Console.Write("\n");
    }

    async Task afisareJucatoriEchipa()
    {
        try
        {
            Console.WriteLine("Numele echipei: ");
            string numeEchipa = Console.ReadLine() ?? string.Empty;

            var echipa = (await srvEchipa.getAll()).FirstOrDefault(e => e.nume.Equals(numeEchipa, StringComparison.OrdinalIgnoreCase));
            if (echipa == null)
            {
                Console.WriteLine("Echipa nu exista");
                return;
            }

            Console.WriteLine ("\n");
            var jucatori = await srvJucator.getAll();
            foreach (var jucator in jucatori.Where(j => j.getEchipa().getId() == echipa.getId()))
            {
                Console.WriteLine(jucator.getNume());
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
        Console.WriteLine("\n");
    }

    async Task afisareJucatoriActiviiEchipeiMeci()
    {
        try
        {
            Console.WriteLine("Numele echipei: ");
            string numeEchipa = Console.ReadLine() ?? string.Empty;
            Console.WriteLine("ID-ul meciului: ");
            int idMeci = Convert.ToInt32(Console.ReadLine());

            var echipa = (await srvEchipa.getAll()).FirstOrDefault(e => e.nume.Equals(numeEchipa, StringComparison.OrdinalIgnoreCase));
            if (echipa == null)
            {
                Console.WriteLine("Echipa nu exista");
                return;
            }

            var jucatoriActivi = await srvJucatorActiv.getAll();
            foreach (var jucatorActiv in jucatoriActivi.Where(ja => ja.idMeci == idMeci))
            {
                var meci = await srvMeci.getById(jucatorActiv.idMeci);
                var jucator = await srvJucator.getById(jucatorActiv.idJucator);
                if (jucator.getEchipa().getId() == echipa.getId())
                {
                    Console.WriteLine(jucator.getNume() + " " + meci.Echipa1.nume + " vs " + meci.Echipa2.nume + " puncte: " + jucatorActiv.nrPuncteInscrise + " " + jucatorActiv.tip);
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
        Console.Write("\n");
    }

    async Task afisareMeciuriPerioada()
    {
        try
        {
            Console.WriteLine("Data de inceput (format YYYY-MM-DD): ");
            DateTime startDate = DateTime.Parse(Console.ReadLine());
            Console.WriteLine("Data de sfarsit (format YYYY-MM-DD): ");
            DateTime endDate = DateTime.Parse(Console.ReadLine());

            var meciuri = await srvMeci.getAll();
            foreach (var meci in meciuri.Where(m => m.Data >= startDate && m.Data <= endDate))
            {
                Console.WriteLine(meci.Echipa1.nume + " vs " + meci.Echipa2.nume + " " + meci.Data);
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
        Console.Write("\n");
    }

    async Task afisareScorMeci()
    {
        try
        {
            Console.WriteLine("ID-ul meciului: ");
            int idMeci = Convert.ToInt32(Console.ReadLine());

            var jucatoriActivi = await srvJucatorActiv.getAll();
            int scorEchipa1 = 0;
            int scorEchipa2 = 0;

            foreach (var jucatorActiv in jucatoriActivi.Where(ja => ja.idMeci == idMeci))
            {
                var jucator = await srvJucator.getById(jucatorActiv.idJucator);
                var meci = await srvMeci.getById(idMeci);

                if (jucator.getEchipa().getId() == meci.Echipa1.getId())
                { 
                    scorEchipa1 += jucatorActiv.nrPuncteInscrise;
                }
                else if (jucator.getEchipa().getId() == meci.Echipa2.getId())
                {
                    scorEchipa2 += jucatorActiv.nrPuncteInscrise;
                }
            }

            var meciFinal = await srvMeci.getById(idMeci);
            Console.WriteLine(meciFinal.Echipa1.nume + " " + scorEchipa1 + " vs " + meciFinal.Echipa2.nume + " " + scorEchipa2);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare: {ex.Message}");
        }
        Console.Write("\n");
    }


    public async Task run()
    {
        meniu();
        bool cond = true;
        while (cond)
        {
            try
            {
                Console.WriteLine("Ce optiune alegi? ");
                string input = Console.ReadLine();
                Console.WriteLine("\n");
                switch (input)
                {
                    case "1":
                        addEchipa();
                        break;
                    case "2":
                        addElev();
                        break;
                    case "3":
                        addJucator();
                        break;
                    case "4":
                        addMeci();
                        break;
                    case "5":
                        addJucatorActiv();
                        break;
                    case "6":
                        afisareechipe();
                        break;
                    case "7":
                        afisareelevi();
                        break;
                    case "8":
                        afisarejucatori();
                        break;
                    case "9":
                        afisaremeciuri();
                        break;
                    case "10":
                        afisarejucatoriactivii();
                        break;
                    case "11":
                        afisareJucatoriEchipa();
                        break;
                    case "12":
                        afisareJucatoriActiviiEchipeiMeci();
                        break;
                    case "13":
                        afisareMeciuriPerioada();
                        break;
                    case "14":
                         afisareScorMeci();
                        break;
                    case "15":
                        cond = false;
                        break;
                    default:
                        Console.WriteLine("Optiune incorecta");
                        break;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"A aparut o eroare: {ex.Message}");
            }
        }
    }

}