package ro.mpp.labfx.service;

import ro.mpp.labfx.domain.Echipa;
import ro.mpp.labfx.repository.EchipaRepository;
import java.util.List;

public class EchipaService {
    private EchipaRepository echipaRepository;

    public EchipaService(EchipaRepository echipaRepository) {
        this.echipaRepository = echipaRepository;
    }

    public List<Echipa> getEchipe() {
        return echipaRepository.findAll();
    }

    public Echipa findByNume(String ech){
        return echipaRepository.findByNume(ech);
    }

    public void adauga(Echipa echipa) {
        echipaRepository.adauga(echipa);
    }

    public void sterge(int id) { echipaRepository.sterge(id);}
}
