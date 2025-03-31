package ro.mpp.labfx.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.labfx.domain.Echipa;
import ro.mpp.labfx.service.EchipaService;

public class MainValidator implements ValidatorInterface<Echipa> {
    private static final Logger logger = LogManager.getLogger(MainValidator.class);
    private final EchipaService echipaService;

    public MainValidator(EchipaService echipaService) {
        this.echipaService = echipaService;
    }

    @Override
    public void validate(Echipa echipa) throws ValidationException {
        if (echipaService.findByNume(echipa.getNume()) == null) {
            logger.error("Echipa cu numele " + echipa.getNume() + " nu exista");
            throw new ValidationException("Echipa cu numele " + echipa.getNume() + " nu exista");
        }
    }
}
