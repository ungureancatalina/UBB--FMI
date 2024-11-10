package domain;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;

    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "prenume='" + firstName + '\'' +
                ", nume='" + lastName + '\'' +
                ", prieteni="  +
                '}';
    }

}