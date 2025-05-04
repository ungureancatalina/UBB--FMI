namespace Model.mpp.model;

public interface IDentifiable<TId>
{
    void SetId(TId id);
    TId GetId();
}
