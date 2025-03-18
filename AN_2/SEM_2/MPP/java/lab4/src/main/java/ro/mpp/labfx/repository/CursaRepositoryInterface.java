package ro.mpp.labfx.repository;

import ro.mpp.labfx.domain.Cursa;
import java.util.List;

public interface CursaRepositoryInterface extends RepositoryInterface<Integer,Cursa> {
    List<Cursa> findByCapacitateMinima(int capacitate);
}
