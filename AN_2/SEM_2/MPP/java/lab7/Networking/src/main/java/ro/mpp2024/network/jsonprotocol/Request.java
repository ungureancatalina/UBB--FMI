package ro.mpp2024.network.jsonprotocol;

import ro.mpp2024.model.*;
import ro.mpp2024.network.jsonprotocol.RequestType;

import java.util.List;

public class Request {
    private RequestType type;
    private Utilizator utilizator;
    private Echipa echipa;
    private ParticipantCursa participantCursa;
    private Participant participant;
    private Cursa cursa;

    private String username;
    private String numeEchipa;
    private String numeParticipant;
    private int nrparticipant;

    private List<ParticipantCursa> participantiCurse;
    private List<Cursa> curse;
    private List<CursaDTO> curseDTO;
    private List<Participant> participanti;

    public Request(){}

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public Echipa getEchipa() {
        return echipa;
    }

    public void setEchipa(Echipa echipa) {
        this.echipa = echipa;
    }

    public ParticipantCursa getParticipantCursa() {
        return participantCursa;
    }

    public void setParticipantCursa(ParticipantCursa participantCursa) {
        this.participantCursa = participantCursa;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Cursa getCursa() {
        return cursa;
    }

    public void setCursa(Cursa cursa) {
        this.cursa = cursa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    public String getNumeParticipant() {
        return numeParticipant;
    }

    public void setNumeParticipant(String numeParticipant) {
        this.numeParticipant = numeParticipant;
    }

    public int getNrparticipant() {
        return nrparticipant;
    }

    public void setNrparticipant(int nrparticipant) {
        this.nrparticipant = nrparticipant;
    }

    public List<ParticipantCursa> getParticipantiCurse() {
        return participantiCurse;
    }

    public void setParticipantiCurse(List<ParticipantCursa> participantiCurse) {
        this.participantiCurse = participantiCurse;
    }

    public List<Cursa> getCurse() {
        return curse;
    }

    public void setCurse(List<Cursa> curse) {
        this.curse = curse;
    }

    public List<CursaDTO> getCurseDTO() {
        return curseDTO;
    }

    public void setCurseDTO(List<CursaDTO> curseDTO) {
        this.curseDTO = curseDTO;
    }

    public List<Participant> getParticipanti() {
        return participanti;
    }

    public void setParticipanti(List<Participant> participanti) {
        this.participanti = participanti;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", utilizator=" + utilizator +
                ", echipa=" + echipa +
                ", participantCursa=" + participantCursa +
                ", participant=" + participant +
                ", cursa=" + cursa +
                ", username='" + username + '\'' +
                ", numeEchipa='" + numeEchipa + '\'' +
                ", numeParticipant='" + numeParticipant + '\'' +
                ", nrparticipant=" + nrparticipant +
                ", participantiCurse=" + participantiCurse +
                ", curse=" + curse +
                ", curseDTO=" + curseDTO +
                ", participanti=" + participanti +
                '}';
    }

}
