package com.example.lab6fx.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;
    private String password;
    private List<Utilizator> prieteni;

    public Utilizator() {
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.prieteni = new ArrayList<>();
    }


    public Utilizator(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.prieteni = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Utilizator> getPrieteni() {
        return prieteni;
    }

    public void addPrieten(Utilizator prieten) {
        this.prieteni.add(prieten);
    }

    public void removePrieten(Utilizator prieten) {
        this.prieteni.remove(prieten);
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id='" + id +'\'' +
                "prenume='" + firstName + '\'' +
                ", nume='" + lastName + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getPrieteni().equals(that.getPrieteni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getPrieteni());
    }
}