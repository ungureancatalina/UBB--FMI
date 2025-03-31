package ro.mpp.labfx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.mpp.labfx.controller.LoginController;
import ro.mpp.labfx.domain.Utilizator;
import ro.mpp.labfx.repository.UtilizatorRepository;
import ro.mpp.labfx.service.UtilizatorService;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController;
    private UtilizatorService utilizatorService;
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private Properties properties;

    @BeforeEach
    void setUp() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("bd.config"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load", e);
        }
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(properties);
        utilizatorService = new UtilizatorService(utilizatorRepository);
        loginController = new LoginController();
        loginController.setter(utilizatorService, null, null, null, null, null, null, null);
    }

    @Test
    void testLoginSuccessful() {
        String username = "cata";
        String password = "parola";
        Utilizator utilizator = utilizatorService.autentificare(username, password);
        assertNotNull(utilizator, "Utilizatorul ar trebui să fie autentificat");
        assertEquals(username, utilizator.getNume(), "Numele de utilizator nu corespunde");

    }

    @Test
    void testLoginFailed() {
        String username = "wrongUser";
        String password = "wrongPassword";
        Utilizator utilizator = utilizatorService.autentificare(username, password);
        assertNull(utilizator, "Autentificarea ar trebui să eșueze pentru datele greșite");
    }

    @Test
    void testLoginLoggerInfoEnabled() {
        String username = "cata";
        String password = "parola";

        utilizatorService.autentificare(username, password);
        assertTrue(logger.isInfoEnabled(), "Log-ul Info nu este activat");
    }

    @Test
    void testLoginLoggerErrorEnabled() {
        String username = "wrongUser";
        String password = "wrongPassword";

        utilizatorService.autentificare(username, password);
        assertTrue(logger.isErrorEnabled(), "Log-ul Error nu este activat");
    }
}
