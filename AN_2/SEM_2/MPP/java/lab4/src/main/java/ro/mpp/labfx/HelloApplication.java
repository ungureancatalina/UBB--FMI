package ro.mpp.labfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp.labfx.controller.LoginController;
import ro.mpp.labfx.repository.UtilizatorRepository;
import ro.mpp.labfx.repository.CursaRepository;
import ro.mpp.labfx.repository.ParticipantRepository;
import ro.mpp.labfx.repository.ParticipantCursaRepository;
import ro.mpp.labfx.repository.EchipaRepository;
import ro.mpp.labfx.service.*;
import ro.mpp.labfx.utils.ConfigUtils;
import ro.mpp.labfx.validator.InscriereValidator;
import ro.mpp.labfx.validator.LoginValidare;
import ro.mpp.labfx.validator.MainValidator;

import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    private static UtilizatorService utilizatorService;
    private static CursaService cursaService;
    private static ParticipantService participantService;
    private static EchipaService echipaService;
    private static ParticipantCursaService participantCursaService;
    private static InscriereValidator inscriereValidator;
    private static LoginValidare loginValidare;
    private static MainValidator mainValidator;

    @Override
    public void start(Stage stage) throws IOException {
        initializeServices();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoginController loginController = fxmlLoader.getController();
        loginController.setter(utilizatorService, cursaService, participantService, echipaService,participantCursaService,inscriereValidator, loginValidare, mainValidator);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private void initializeServices() {
        try {
            Properties properties = ConfigUtils.loadProperties();
            UtilizatorRepository utilizatorRepository = new UtilizatorRepository(properties);
            CursaRepository cursaRepository = new CursaRepository(properties);
            ParticipantRepository participantRepository = new ParticipantRepository(properties);
            EchipaRepository echipaRepository = new EchipaRepository(properties);
            ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository(properties);
            utilizatorService = new UtilizatorService(utilizatorRepository);
            cursaService = new CursaService(cursaRepository, participantCursaRepository);
            participantService = new ParticipantService(participantRepository);
            echipaService = new EchipaService(echipaRepository);
            participantCursaService = new ParticipantCursaService(participantCursaRepository);
            inscriereValidator = new InscriereValidator(participantService);
            loginValidare = new LoginValidare();
            mainValidator = new MainValidator(echipaService);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize services", e);
        }
    }

    public static void main(String[] args) {
        try {
            Properties properties = ConfigUtils.loadProperties();

            UtilizatorRepository utilizatorRepository = new UtilizatorRepository(properties);
            CursaRepository cursaRepository = new CursaRepository(properties);
            ParticipantRepository participantRepository = new ParticipantRepository(properties);
            EchipaRepository echipaRepository = new EchipaRepository(properties);
            ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository(properties);

            utilizatorService = new UtilizatorService(utilizatorRepository);
            cursaService = new CursaService(cursaRepository, participantCursaRepository);
            participantService = new ParticipantService(participantRepository);
            echipaService = new EchipaService(echipaRepository);
            participantCursaService = new ParticipantCursaService(participantCursaRepository);

            launch();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties", e);
        }
    }
}
