package domain.validator;

import domain.Utilizator;


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
        System.out.println(error);
        if(!error.equals(""))
            throw new ValidationException(error);
    }
}
