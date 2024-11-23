package com.example.lab6fx.domain.validator;

import com.example.lab6fx.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator>
{
    @Override
    public void validate(Utilizator entity) throws ValidationException
    {
        String error="";
        if(entity.getFirstName().isEmpty())
            error=error+" Prenumele nu poate fi null";
        if(entity.getLastName().isEmpty())
            error=error+" Numele nu poate fi null";
        if(!entity.getFirstName().matches("[A-Z][a-z]*"))
            error=error+" Prenumele trebuie sa inceapa cu litera mare";
        if(!entity.getLastName().matches("[A-Z][a-z]*"))
            error=error+" Numele trebuie sa inceapa cu litera mare";
        System.out.println(error);
        if(!error.equals(""))
            throw new ValidationException(error);
    }
}
