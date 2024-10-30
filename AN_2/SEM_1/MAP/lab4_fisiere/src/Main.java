import domain.Prietenie;
import domain.Utilizator;
import domain.validator.PrietenieValidator;
import domain.validator.UtilizatorValidator;
import repository.InMemoryRepository;
import repository.PrietenieFileRepository;
import repository.UtilizatorFileRepository;
import service.UtilizatorService;
import ui.Console;

public class Main
{
    public static void main(String[] args) {

        UtilizatorFileRepository repo_ut=new UtilizatorFileRepository("data/utilizatori.csv", new UtilizatorValidator());
        PrietenieFileRepository repo_pr= new PrietenieFileRepository("data/prietenii.csv", new PrietenieValidator(repo_ut));
        UtilizatorService serv_ut= new UtilizatorService(repo_ut,repo_pr);
        Console console = new Console(serv_ut);
        console.run();

    }

}


