package ro.mpp2024.server;

import ro.mpp2024.model.*;
import ro.mpp2024.persistence.*;
import ro.mpp2024.services.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImpl implements IServices {

    private CursaRepositoryInterface cursaRepository;
    private EchipaRepositoryInterface echipaRepository;
    private ParticipantRepositoryInterface participantRepository;
    private ParticipantCursaRepositoryInterface participantCursaRepository;
    private UtilizatorRepositoryInterface utilizatorRepository;

    private Map<String, IObserver> loggedObservers;

    public ServicesImpl(UtilizatorRepositoryInterface utilizatorRepository, EchipaRepositoryInterface echipaRepository, ParticipantRepositoryInterface participantRepository, CursaRepositoryInterface cursaRepository, ParticipantCursaRepositoryInterface participantCursaRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.echipaRepository = echipaRepository;
        this.participantRepository = participantRepository;
        this.cursaRepository = cursaRepository;
        this.participantCursaRepository = participantCursaRepository;
        loggedObservers = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Utilizator login(String nume, String parola, IObserver client) throws Exception {
        Utilizator utilizator = utilizatorRepository.findByNume(nume);
        if (loggedObservers.containsKey(nume)) {
            throw new Exception("Utilizatorul este deja conectat.");
        }
        if (utilizator != null && utilizator.verificaParola(parola)) {
            loggedObservers.put(nume, client);
            return utilizator;
        }
        throw new Exception("Autentificare esuata.");
    }

    @Override
    public synchronized void inscriereParticipant(Participant participant) throws Exception {
        participantRepository.adauga(participant);
    }

    @Override
    public synchronized List<Participant> findAllParticipanti() throws Exception {
        return participantRepository.findAll();
    }

    @Override
    public synchronized List<Participant> cautaParticipantiDupaEchipa(String numeEchipa) throws Exception {
        Echipa echipa = echipaRepository.findByNume(numeEchipa);
        if (echipa == null) {
            throw new Exception("Echipa nu exista");
        }
        return participantRepository.findByEchipa(echipa);
    }


    @Override
    public synchronized Echipa findEchipaByNume(String nume) throws Exception {
        return echipaRepository.findByNume(nume);
    }

    @Override
    public synchronized void adaugaEchipa(Echipa echipa) throws Exception {
        echipaRepository.adauga(echipa);
    }

    @Override
    public synchronized List<Cursa> findAllCurse() throws Exception {
        return cursaRepository.findAll();
    }

    @Override
    public synchronized List<CursaDTO> listDtoCurse() throws Exception {
        List<Cursa> curse = cursaRepository.findAll();
        List<CursaDTO> dtoList = new ArrayList<>();
        for (Cursa cursa : curse) {
            int nrPart = participantCursaRepository.findNrPartByCursa(cursa);
            dtoList.add(new CursaDTO(cursa.getNume(), cursa.getCapacitateMinima(), cursa.getCapacitateMaxima(), nrPart));
        }
        return dtoList;
    }

    @Override
    public synchronized int findNrPartByCursa(Cursa cursa) throws Exception {
        return participantCursaRepository.findNrPartByCursa(cursa);
    }

    private void notifyParticipantAdded(Participant participant, Cursa cursa) {
        for (IObserver obs : loggedObservers.values()) {
            try {
                obs.adauga(participant, cursa);
            } catch (Exception e) {
                System.out.println("Error notifying observer: " + e.getMessage());
            }
        }
    }

    @Override
    public synchronized void adaugaParticipantLaCursa(Participant participant, Cursa cursa) throws Exception {
        participantCursaRepository.adaugainbaza(participant, cursa);
        notifyParticipantAdded(participant, cursa);
    }

    @Override
    public synchronized List<ParticipantCursa> findAllParticipantCursa() throws Exception {
        return participantCursaRepository.findAll();
    }

}