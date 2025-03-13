package ro.mpp2024.domain;

public class ParticipantCursa {
    private Participant part;
    private Cursa cursa;

    public ParticipantCursa(Participant part, Cursa cursa) {
        this.part = part;
        this.cursa = cursa;
    }

    public Participant getParticipant() {
        return part;
    }

    public void setParticipant(Participant part) {
        this.part = part;
    }

    public Cursa getCursa() {
        return cursa;
    }

    public void setCursa(Cursa cursa) {
        this.cursa = cursa;
    }
}
