package ro.mpp2024.persistence.repository;

import org.hibernate.Session;
import ro.mpp2024.model.Utilizator;
import ro.mpp2024.persistence.UtilizatorRepositoryInterface;

import java.util.List;

public class UtilizatorORMRepository implements UtilizatorRepositoryInterface {

    @Override
    public void adauga(Utilizator utilizator) {
        utilizator.setParolaCriptata(utilizator.getParola());
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(utilizator));
    }

    @Override
    public void sterge(Integer id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Utilizator utilizator = session.find(Utilizator.class, id);
            if (utilizator != null) session.remove(utilizator);
        });
    }

    @Override
    public void modifica(Utilizator utilizator) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(utilizator));
    }

    @Override
    public Utilizator findById(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Utilizator.class, id);
        }
    }

    @Override
    public List<Utilizator> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Utilizator", Utilizator.class).list();
        }
    }

    @Override
    public Utilizator findByNume(String nume) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Utilizator where nume = :nume", Utilizator.class)
                    .setParameter("nume", nume)
                    .uniqueResult();
        }
    }
}
