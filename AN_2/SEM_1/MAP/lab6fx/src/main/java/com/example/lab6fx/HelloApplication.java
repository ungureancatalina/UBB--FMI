package com.example.lab6fx;

import com.example.lab6fx.controller.LoginController;
import com.example.lab6fx.controller.UtilizatorController;
import com.example.lab6fx.domain.Cerere;
import com.example.lab6fx.domain.Prietenie;
import com.example.lab6fx.domain.Tuple;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.domain.validator.CerereValidator;
import com.example.lab6fx.domain.validator.PrietenieValidator;
import com.example.lab6fx.domain.validator.UtilizatorValidator;
import com.example.lab6fx.repository.PagingRepository;
import com.example.lab6fx.repository.Repository;
import com.example.lab6fx.repository.database.CerereRepositoryDB;
import com.example.lab6fx.repository.database.PrietenieRepositoryDB;
import com.example.lab6fx.repository.database.UtilizatorRepositoryDB;
import com.example.lab6fx.service.CerereService;
import com.example.lab6fx.service.PrietenieService;
import com.example.lab6fx.service.UtilizatorService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application
{
    String url = "jdbc:postgresql://localhost:5432/lab6";
    String password = "catalina";
    String username= "postgres";

    PagingRepository<Long, Utilizator> repo_ut= new UtilizatorRepositoryDB(new UtilizatorValidator(),url, username, password);
    Repository<Tuple<Long, Long>, Prietenie> repo_pr = new PrietenieRepositoryDB(new PrietenieValidator(),url, username, password);
    Repository<Long, Cerere> repo_ce = new CerereRepositoryDB(new CerereValidator(),url, username, password);
    UtilizatorService serv_ut= new UtilizatorService(repo_ut,repo_pr);
    PrietenieService serv_pr = new PrietenieService(repo_ut,repo_pr);
    CerereService serv_ce = new CerereService(repo_ce);

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("S-a conectat la baza de date");
        } catch (SQLException e) {
            System.out.println("Eroare la conectarea la baze de date");
            e.printStackTrace();
        }
        initLoginView(stage);

        stage.setWidth(1000);
        stage.setTitle("Aplicatie");
        stage.show();
    }


    private void initLoginView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();

        LoginController loginController = loginLoader.getController();
        loginController.setService(serv_ut, primaryStage);
        loginController.setutilizator_autentificat(() -> {
            try {
                openUtilizatorWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void openUtilizatorWindow() throws IOException {
        FXMLLoader utilizatorLoader = new FXMLLoader(getClass().getResource("/utilizatori.fxml"));
        Scene utilizatorScene = new Scene(utilizatorLoader.load());

        Stage utilizatorStage = new Stage();
        utilizatorStage.setScene(utilizatorScene);
        utilizatorStage.setTitle("Utilizatori");
        utilizatorStage.show();

        UtilizatorController utilizatorController = utilizatorLoader.getController();
        try {
            utilizatorController.setAplicatie(serv_ut, serv_pr, serv_ce);
            utilizatorController.setPrimaryStage(utilizatorStage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


