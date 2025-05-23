package ro.mpp2024.server;

import ro.mpp2024.model.*;
import ro.mpp2024.persistence.*;
import ro.mpp2024.services.*;

import java.util.*;
import java.util.concurrent.*;

public class ServicesImpl implements IServices {

    private final CursaRepositoryInterface cursaRepository;
    private final EchipaRepositoryInterface echipaRepository;
    private final ParticipantRepositoryInterface participantRepository;
    private final ParticipantCursaRepositoryInterface participantCursaRepository;
    private final UtilizatorRepositoryInterface utilizatorRepository;

    private final Map<String, List<IObserver>> loggedClients = new ConcurrentHashMap<>();

    public ServicesImpl(UtilizatorRepositoryInterface utilizatorRepository, EchipaRepositoryInterface echipaRepository, ParticipantRepositoryInterface participantRepository, CursaRepositoryInterface cursaRepository, ParticipantCursaRepositoryInterface participantCursaRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.echipaRepository = echipaRepository;
        this.participantRepository = participantRepository;
        this.cursaRepository = cursaRepository;
        this.participantCursaRepository = participantCursaRepository;
    }

    @Override
    public synchronized void login(Utilizator utilizator, IObserver client) throws Exception {
        if (utilizator == null)
            throw new Exception("Utilizator invalid.");

        try {
            Utilizator found = utilizatorRepository.findByNume(utilizator.getNume());
            if (found == null) {
                System.out.println("Utilizatorul nu a fost găsit: " + utilizator.getNume());
                throw new Exception("Utilizator inexistent.");
            }

            boolean parolaOK = found.verificaParola(utilizator.getParola());
            if (!parolaOK) {
                throw new Exception("Parola incorecta.");
            }

            loggedClients.putIfAbsent(utilizator.getNume(), new CopyOnWriteArrayList<>());
            List<IObserver> observers = loggedClients.get(utilizator.getNume());

            if (observers.contains(client)) {
                throw new Exception("Observer deja logat.");
            }

            observers.add(client);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Eroare autentificare: " + e.getMessage());
        }
    }

    @Override
    public synchronized void logout(Utilizator utilizator, IObserver client) {
        if (utilizator == null) return;

        List<IObserver> observers = loggedClients.get(utilizator.getNume());
        if (observers != null) {
            observers.remove(client);
            if (observers.isEmpty()) {
                loggedClients.remove(utilizator.getNume());
            }
        }

        System.out.println("Utilizator delogat: " + utilizator.getNume());
    }

    @Override
    public synchronized Participant findByNume(String nume) {
        return participantRepository.findByNume(nume);
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
        if (echipa == null) throw new Exception("Echipa nu exista");
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

    @Override
    public synchronized void adaugaParticipantLaCursa(ParticipantCursa participantCursa) throws Exception {
        participantCursaRepository.adaugainbaza(participantCursa.getParticipant(), participantCursa.getCursa());
        notifyParticipantAdded(participantCursa);
    }

    @Override
    public synchronized List<ParticipantCursa> findAllParticipantCursa() throws Exception {
        return participantCursaRepository.findAll();
    }

    private void notifyParticipantAdded(ParticipantCursa participantCursa) {
        for (List<IObserver> observerList : loggedClients.values()) {
            for (IObserver obs : observerList) {
                new Thread(() -> {
                    try {
                        obs.adauga(participantCursa.getParticipant(), participantCursa.getCursa());
                    } catch (Exception e) {
                        System.out.println("Error notifying observer: " + e.getMessage());
                    }
                }).start();
            }
        }
    }
}
