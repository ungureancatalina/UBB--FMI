package ro.mpp2024.repository;

import ro.mpp2024.domain.Cursa;
import java.util.List;

public interface CursaRepositoryInterface extends RepositoryInterface<Integer,Cursa> {
    List<Cursa> findByCapacitateMinima(int capacitate);
}
