package ro.mpp.labfx.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.labfx.domain.Utilizator;

public class LoginValidare implements ValidatorInterface<Utilizator> {
    private static final Logger logger = LogManager.getLogger(LoginValidare.class);

    public void validate(Utilizator utilizator) throws ValidationException {
        if (utilizator.getNume() == null || utilizator.getNume().trim().length() < 4) {
            logger.error("Username-ul trebuie să aibă cel puțin 4 caractere");
            throw new ValidationException("Username-ul trebuie să aibă cel puțin 4 caractere");
        }

        if (utilizator.getParola() == null || !utilizator.getParola().matches("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$")) {
            logger.error("Parola trebuie să conțină atât litere, cât și cifre și să aibă cel puțin 6 caractere");
            throw new ValidationException("Parola trebuie să conțină atât litere, cât și cifre și să aibă cel puțin 6 caractere");
        }
    }
}
