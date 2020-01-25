package bd2app.dao;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.UzytkownicyEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class UzytkownicyDao implements Dao<UzytkownicyEntity> {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public UzytkownicyDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(UzytkownicyEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(UzytkownicyEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        //entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    public void refresh(UzytkownicyEntity entity){
        entityManager.refresh(entity);
    }

    @Override
    public UzytkownicyEntity findById(int id) {
        TypedQuery<UzytkownicyEntity> query = entityManager.createQuery(
                "SELECT c FROM UzytkownicyEntity c WHERE c.id = :id", UzytkownicyEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(UzytkownicyEntity entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<UzytkownicyEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM UzytkownicyEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }

    public int login(String email, String password, int typ) {
        for (UzytkownicyEntity e : findAll()
                ) {

            if (e.getHasło().equals(password) && e.getEmail().equals(email)&&e.getTypUzytkownika()==typ)
                return e.getId();
        }
        return -1;
    }

    public boolean validateDuplicate(String email, String pesel) {
        for (UzytkownicyEntity e : findAll()
                ) {
            if (e.getPesel().equals(pesel) || e.getEmail().equals(email))
                return true;
        }
        return false;
    }
}
