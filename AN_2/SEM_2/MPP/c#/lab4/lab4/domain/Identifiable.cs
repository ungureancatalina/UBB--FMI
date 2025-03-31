namespace lab4.domain;

public interface IDentifiable<TId>
{
    void SetId(TId id);
    TId GetId();
}
