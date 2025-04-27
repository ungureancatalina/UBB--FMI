package ro.mpp2024.model;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
