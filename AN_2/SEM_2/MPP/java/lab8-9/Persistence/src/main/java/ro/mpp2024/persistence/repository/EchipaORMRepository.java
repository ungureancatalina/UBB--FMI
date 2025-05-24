package ro.mpp2024.persistence.repository;

import org.hibernate.Session;
import ro.mpp2024.model.Echipa;
import ro.mpp2024.persistence.EchipaRepositoryInterface;

import java.util.List;

public class EchipaORMRepository implements EchipaRepositoryInterface {

    @Override
    public void adauga(Echipa echipa) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(echipa));
    }

    @Override
    public void sterge(Integer id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Echipa echipa = session.find(Echipa.class, id);
            if (echipa != null) session.remove(echipa);
        });
    }

    @Override
    public void modifica(Echipa echipa) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(echipa));
    }

    @Override
    public Echipa findById(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Echipa.class, id);
        }
    }

    @Override
    public List<Echipa> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Echipa", Echipa.class).list();
        }
    }

    @Override
    public Echipa findByNume(String nume) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Echipa where nume = :nume", Echipa.class)
                    .setParameter("nume", nume)
                    .uniqueResult();
        }
    }

    @Override
    public List<Echipa> findAllOrderedByNume() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Echipa order by nume", Echipa.class).list();
        }
    }
}
