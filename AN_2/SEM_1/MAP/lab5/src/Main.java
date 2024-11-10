import domain.validator.PrietenieValidator;
import domain.validator.UtilizatorValidator;
import repository.database.PrietenieRepositoryDB;
import repository.database.UtilizatorRepositoryDB;
import service.UtilizatorService;
import ui.Console;

public class Main
{
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/lab5_map";
        String password = "catalina";
        String username= "postgres";

        UtilizatorRepositoryDB repo_ut= new UtilizatorRepositoryDB(new UtilizatorValidator(),url, username, password);
        PrietenieRepositoryDB repo_pr = new PrietenieRepositoryDB(new PrietenieValidator(repo_ut),url, username, password);
        UtilizatorService serv_ut= new UtilizatorService(repo_ut,repo_pr);
        Console console = new Console(serv_ut);
        console.run();

    }

}


