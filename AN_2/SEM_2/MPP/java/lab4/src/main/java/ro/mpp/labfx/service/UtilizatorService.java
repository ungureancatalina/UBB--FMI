package ro.mpp.labfx.service;

import ro.mpp.labfx.domain.Utilizator;
import ro.mpp.labfx.repository.UtilizatorRepository;

public class UtilizatorService {
    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public Utilizator autentificare(String username, String password) {
        Utilizator utilizator = utilizatorRepository.findByNume(username);
        if (utilizator != null && utilizator.verificaParola(password)) {
            return utilizator;
        }
        return null;
    }

    public Utilizator findbyid(int id) {
        return utilizatorRepository.findById(id);
    }
}
