package ro.mpp2024.network.jsonprotocol;

import java.util.List;
import ro.mpp2024.model.*;
import ro.mpp2024.network.jsonprotocol.ResponseType;

public class Response {
    private ResponseType type;
    private String errorMessage;
    private Utilizator utilizator;
    private Echipa echipa;
    private ParticipantCursa participantCursa;
    private Participant participant;
    private Cursa cursa;

    private int nrparticipant;

    private List<ParticipantCursa> participantiCurse;
    private List<Cursa> curse;
    private List<CursaDTO> curseDTO;
    private List<Participant> participanti;
    private List<ParticipantDTO> participantiDTO;

    public Response() {}

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public void setParticipantiDTO(List<ParticipantDTO> participantiDTO) {
        this.participantiDTO = participantiDTO;
    }

    public List<Participant> getParticipanti() {
        return participanti;
    }

    public void setParticipanti(List<Participant> participanti) {
        this.participanti = participanti;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                ", utilizator=" + utilizator +
                '}';
    }
}
