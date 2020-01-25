package bd2app.dao;

import bd2app.App;
import bd2app.model.InstruktorzyEntity;
import bd2app.model.UzytkownicyEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class InstruktorzyDao implements Dao<InstruktorzyEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public InstruktorzyDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(InstruktorzyEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(InstruktorzyEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public InstruktorzyEntity findById(int id) {
        TypedQuery<InstruktorzyEntity> query = entityManager.createQuery(
                "SELECT c FROM InstruktorzyEntity c WHERE c.id = :id", InstruktorzyEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(InstruktorzyEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<InstruktorzyEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM InstruktorzyEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }

    public int login(String email, String password) {
        for (InstruktorzyEntity e : findAll()
                ) {
            if (e.getHasło().equals(password) && e.getEmail().equals(email))
                return e.getId();
        }
        return -1;
    }
}
