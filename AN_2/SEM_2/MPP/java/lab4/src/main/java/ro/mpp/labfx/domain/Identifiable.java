package ro.mpp.labfx.domain;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
