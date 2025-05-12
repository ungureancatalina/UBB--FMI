package ro.mpp2024.services;

import ro.mpp2024.model.Cursa;
import ro.mpp2024.model.Participant;
import ro.mpp2024.model.ParticipantCursa;

public interface IObserver {
    void adauga(Participant participant, Cursa cursa) throws Exception;
}