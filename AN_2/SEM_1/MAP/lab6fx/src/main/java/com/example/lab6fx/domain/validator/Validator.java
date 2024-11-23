package com.example.lab6fx.domain.validator;

public interface Validator<T> {
    void validate(T entity)
            throws ValidationException;
}