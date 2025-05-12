package ro.mpp2024.persistence;

import ro.mpp2024.model.Utilizator;

public interface UtilizatorRepositoryInterface extends RepositoryInterface<Integer,Utilizator> {
    Utilizator findByNume(String nume);
}
