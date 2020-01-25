package bd2app.dao;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.RezerwacjeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class RezerwacjeDao implements Dao<RezerwacjeEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RezerwacjeDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(RezerwacjeEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(RezerwacjeEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public RezerwacjeEntity findById(int id) {
        TypedQuery<RezerwacjeEntity> query = entityManager.createQuery(
                "SELECT c FROM RezerwacjeEntity c WHERE c.id = :id", RezerwacjeEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(RezerwacjeEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<RezerwacjeEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM RezerwacjeEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }
}