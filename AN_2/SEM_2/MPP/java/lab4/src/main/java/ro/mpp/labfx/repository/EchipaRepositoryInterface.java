package ro.mpp.labfx.repository;

import ro.mpp.labfx.domain.Echipa;
import java.util.List;

public interface EchipaRepositoryInterface extends RepositoryInterface<Integer,Echipa> {
    Echipa findByNume(String nume);
    List<Echipa> findAllOrderedByNume();
}
