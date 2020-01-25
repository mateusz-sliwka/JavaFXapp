package bd2app.dao;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.InstruktorzyKategoriaEntity;
import bd2app.model.KategoriaEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KategoriaEntityDao implements Dao {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public KategoriaEntityDao(EntityManagerFactory emf, EntityManager em) {
        entityManagerFactory = emf;
        entityManager = em;
    }

    @Override
    public void persist(Object entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public void update(Object entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.refresh(entity);
    }

    @Override
    public KategoriaEntity findById(int id) {
        TypedQuery<KategoriaEntity> query = entityManager.createQuery(
                "SELECT c FROM KategoriaEntity c WHERE c.id = :id", KategoriaEntity.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Object entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<KategoriaEntity> findAll() {
        List all = entityManager.createQuery("SELECT c FROM KategoriaEntity c").getResultList();
        return all;
    }

    @Override
    public void deleteAll() {
    }

    public List<InstruktorzyEntity> instruktorzyByKategoria(KategoriaEntity k, InstruktorzyDao idao) {
        Collection<InstruktorzyKategoriaEntity> list = k.getInstruktorzyKategoriasById();
        List<InstruktorzyEntity> listaInstruktorow = new ArrayList<InstruktorzyEntity>();
        for (InstruktorzyKategoriaEntity i : list
                ) {
            listaInstruktorow.add(idao.findById(i.getInstruktorzyid()));
        }
        return listaInstruktorow;
    }
}
