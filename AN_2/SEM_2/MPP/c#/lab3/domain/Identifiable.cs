namespace lab3.domain;

public interface Identifiable<ID>
{
    void SetId(ID id);
    ID GetId();
}
