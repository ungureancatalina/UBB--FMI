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

    Repository<Long, Utilizator> repo_ut= new UtilizatorRepositoryDB(new UtilizatorValidator(),url, username, password);
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
        try {
            initView(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stage.setWidth(1000);
        stage.setTitle("Aplicatie");
        stage.show();
    }

    private void initView(Stage primaryStage) throws IOException, SQLException {
        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(fmxlLoader.load());
        primaryStage.setScene(scene);
        LoginController loginController = fmxlLoader.getController();
        loginController.setService(serv_ut, primaryStage);

        loginController.setutilizator_autentificat(() -> {
            try {
                initUtilizatorView(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUtilizatorView(Stage primaryStage) throws IOException {
        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/utilizatori.fxml"));
        Scene scene = new Scene(fmxlLoader.load());
        primaryStage.setScene(scene);

        UtilizatorController utilizatorController = fmxlLoader.getController();
        try {
            utilizatorController.setAplicatie(serv_ut,serv_pr,serv_ce);
            utilizatorController.setPrimaryStage(primaryStage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


