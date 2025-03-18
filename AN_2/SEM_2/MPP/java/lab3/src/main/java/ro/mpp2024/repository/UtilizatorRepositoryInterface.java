package ro.mpp2024.repository;

import ro.mpp2024.domain.Utilizator;

public interface UtilizatorRepositoryInterface extends RepositoryInterface<Integer,Utilizator> {
    Utilizator findByNume(String nume);
}
