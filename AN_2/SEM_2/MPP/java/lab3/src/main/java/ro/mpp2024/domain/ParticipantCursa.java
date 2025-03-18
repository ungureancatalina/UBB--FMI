package ro.mpp2024.domain;

import java.util.Objects;

public class ParticipantCursa{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantCursa that = (ParticipantCursa) o;
        return Objects.equals(part, that.part) && Objects.equals(cursa, that.cursa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(part, cursa);
    }

    @Override
    public String toString() {
        return "ParticipantCursa{" +
                "part=" + part +
                ", cursa=" + cursa +
                '}';
    }
}
