package bd2app.dao;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.UslugiEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class UslugiDao implements Dao<UslugiEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public UslugiDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(UslugiEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(UslugiEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public UslugiEntity findById(int id) {
        TypedQuery<UslugiEntity> query = entityManager.createQuery(
                "SELECT c FROM UslugiEntity c WHERE c.id = :id", UslugiEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(UslugiEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<UslugiEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM UslugiEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }
}
