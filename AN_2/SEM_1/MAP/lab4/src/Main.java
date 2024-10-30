import domain.Prietenie;
import domain.Utilizator;
import domain.validator.PrietenieValidator;
import domain.validator.UtilizatorValidator;
import repository.InMemoryRepository;
import service.UtilizatorService;
import ui.Console;

public class Main
{
    public static void main(String[] args) {

        InMemoryRepository<Long, Utilizator> repository_utilizator =new InMemoryRepository<>(new UtilizatorValidator());
        InMemoryRepository<Long, Prietenie> repository_prietenie =new InMemoryRepository<>(new PrietenieValidator(repository_utilizator));
        UtilizatorService utilizatorService= new UtilizatorService(repository_utilizator, repository_prietenie);
        Console console = new Console(utilizatorService);

       utilizatori(utilizatorService);
       console.run();

    }

    public static void utilizatori(UtilizatorService utilizatorService)
    {
        Utilizator utilizator1= new Utilizator("Popescu", "Ana");
        Utilizator utilizator2= new Utilizator("Marinescu", "Maria");
        Utilizator utilizator3= new Utilizator("Georgescu", "Paula");
        Utilizator utilizator4= new Utilizator("Ionescu", "Andrei");
        Utilizator utilizator5= new Utilizator("Lovinescu", "Marian");
        Utilizator utilizator6= new Utilizator("Eminescu", "Paul");

        utilizatorService.addUtilizator(utilizator1);
        utilizatorService.addUtilizator(utilizator2);
        utilizatorService.addUtilizator(utilizator3);
        utilizatorService.addUtilizator(utilizator4);
        utilizatorService.addUtilizator(utilizator5);
        utilizatorService.addUtilizator(utilizator6);
    }
}


