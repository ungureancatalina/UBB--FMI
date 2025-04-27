package ro.mpp2024.model;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantCursa implements Serializable {
    private Participant participant;
    private Cursa cursa;

    public ParticipantCursa(Participant participant, Cursa cursa) {
        this.participant = participant;
        this.cursa = cursa;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantCursa that = (ParticipantCursa) o;
        return Objects.equals(participant, that.participant) && Objects.equals(cursa, that.cursa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participant, cursa);
    }

    @Override
    public String toString() {
        return "ParticipantCursa{" +
                "participant=" + participant +
                ", cursa=" + cursa +
                '}';
    }
}
