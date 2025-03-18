package ro.mpp.labfx.repository;

import ro.mpp.labfx.domain.Utilizator;

public interface UtilizatorRepositoryInterface extends RepositoryInterface<Integer,Utilizator> {
    Utilizator findByNume(String nume);
}
