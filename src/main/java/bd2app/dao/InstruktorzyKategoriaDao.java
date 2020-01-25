package bd2app.dao;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.InstruktorzyKategoriaEntity;
import bd2app.model.KategoriaEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class InstruktorzyKategoriaDao implements Dao<InstruktorzyKategoriaEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public InstruktorzyKategoriaDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(InstruktorzyKategoriaEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(InstruktorzyKategoriaEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public InstruktorzyKategoriaEntity findById(int id) {
        return null;
    }

    public List<InstruktorzyKategoriaEntity> findByKategoriaId(int id) {
        TypedQuery<InstruktorzyKategoriaEntity> query = entityManager.createQuery(
                "SELECT c FROM InstruktorzyKategoriaEntity c WHERE c.kategoriaid = :id", InstruktorzyKategoriaEntity.class);
        return query.setParameter("id", id).getResultList();
    }

    public List<InstruktorzyKategoriaEntity> findByInstruktorId(int id) {
        TypedQuery<InstruktorzyKategoriaEntity> query = entityManager.createQuery(
                "SELECT c FROM InstruktorzyKategoriaEntity c WHERE c.instruktorzyid = :id", InstruktorzyKategoriaEntity.class);
        return query.setParameter("id", id).getResultList();
    }

    @Override
    public void delete(InstruktorzyKategoriaEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<InstruktorzyKategoriaEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM InstruktorzyKategoriaEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }
}
