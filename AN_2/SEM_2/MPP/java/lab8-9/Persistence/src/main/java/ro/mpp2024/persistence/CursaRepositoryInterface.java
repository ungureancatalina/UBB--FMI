package ro.mpp2024.persistence;

import ro.mpp2024.model.Cursa;

import java.util.List;

public interface CursaRepositoryInterface extends RepositoryInterface<Integer,Cursa> {
    List<Cursa> findByCapacitateMinima(int capacitate);
}
