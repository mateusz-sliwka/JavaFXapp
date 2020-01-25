package bd2app.dao;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.TransakcjeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class TransakcjeDao implements Dao<TransakcjeEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public TransakcjeDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(TransakcjeEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(TransakcjeEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public TransakcjeEntity findById(int id) {
        TypedQuery<TransakcjeEntity> query = entityManager.createQuery(
                "SELECT c FROM TransakcjeEntity c WHERE c.id = :id", TransakcjeEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    public List<TransakcjeEntity> findByUserId(int id) {
        TypedQuery<TransakcjeEntity> query = entityManager.createQuery(
                "SELECT c FROM TransakcjeEntity c WHERE c.uzytkownik = :id", TransakcjeEntity.class);
        return query.setParameter("id", id).getResultList();
    }

    @Override
    public void delete(TransakcjeEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<TransakcjeEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM TransakcjeEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }
}
