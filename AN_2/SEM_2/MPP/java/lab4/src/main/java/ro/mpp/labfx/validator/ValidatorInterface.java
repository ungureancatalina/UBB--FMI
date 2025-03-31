package ro.mpp.labfx.validator;

public interface ValidatorInterface<T> {
    void validate(T entity) throws ValidationException;
}
