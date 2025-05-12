package ro.mpp2024.persistence;

import ro.mpp2024.model.Echipa;

import java.util.List;

public interface EchipaRepositoryInterface extends RepositoryInterface<Integer,Echipa> {
    Echipa findByNume(String nume);
    List<Echipa> findAllOrderedByNume();
}
