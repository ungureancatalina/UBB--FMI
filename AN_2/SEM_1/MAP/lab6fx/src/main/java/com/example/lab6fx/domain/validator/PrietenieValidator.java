package com.example.lab6fx.domain.validator;

import com.example.lab6fx.domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie> {

    @Override
    public void validate(Prietenie entity) throws ValidationException {


        if (entity.getId().getLeft() == null || entity.getId().getRight() == null)
            throw new ValidationException("Id-urile nu pot fi null! ");
    }
}