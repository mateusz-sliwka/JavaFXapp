package bd2app.dao;

import bd2app.model.UslugiEntity;
import bd2app.model.UstawieniaEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class UstawieniaDao implements Dao<UstawieniaEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public UstawieniaDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(UstawieniaEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(UstawieniaEntity entity) {
        System.out.println(entity.getWartosc());
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public UstawieniaEntity findById(int id) {
        TypedQuery<UstawieniaEntity> query = entityManager.createQuery(
                "SELECT c FROM UstawieniaEntity c WHERE c.id = :id", UstawieniaEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    public UstawieniaEntity findByNazwa(String Nazwa) {
        TypedQuery<UstawieniaEntity> query = entityManager.createQuery(
                "SELECT c FROM UstawieniaEntity c WHERE c.nazwa = :Nazwa", UstawieniaEntity.class);
        return query.setParameter("Nazwa", Nazwa).getSingleResult();
    }

    @Override
    public void delete(UstawieniaEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<UstawieniaEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM UstawieniaEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }
}
