package ro.mpp2024.repository;

import ro.mpp2024.domain.Echipa;
import java.util.List;

public interface EchipaRepositoryInterface extends RepositoryInterface<Integer,Echipa> {
    Echipa findByNume(String nume);
    List<Echipa> findAllOrderedByNume();
}
