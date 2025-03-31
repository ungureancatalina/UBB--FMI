package ro.mpp.labfx.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.labfx.domain.Participant;
import ro.mpp.labfx.repository.UtilizatorRepository;
import ro.mpp.labfx.service.ParticipantService;

import javax.xml.validation.Validator;

public class InscriereValidator implements ValidatorInterface<Participant> {
    private static final Logger logger = LogManager.getLogger(UtilizatorRepository.class);
    private final ParticipantService participantService;

    public InscriereValidator(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Override
    public void validate(Participant participant) throws ValidationException {
        if (participant.getNume().trim().isEmpty() || participant.getCNP().trim().isEmpty() || participant.getEchipa() == null) {
            logger.error("Toate câmpurile trebuie completate");
            throw new ValidationException("Toate câmpurile trebuie completate");
        }

        if (!participant.getCNP().matches("\\d{13}")) {
            logger.error("CNP-ul trebuie să conțină exact 13 cifre");
            throw new ValidationException("CNP-ul trebuie să conțină exact 13 cifre");
        }

        if (participantService.findbyNume(participant) != null) {
            logger.error("Numele participantului există deja");
            throw new ValidationException("Numele participantului există deja");
        }
    }
}
