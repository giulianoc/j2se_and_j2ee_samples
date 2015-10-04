package com.entities.test;

// import com.entities.Wine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 22/03/15
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class JavaServiceFacade {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public JavaServiceFacade() {
        this("jpa-example");
    }

    public JavaServiceFacade(String persistenceUnit) {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    /**
     * All changes that have been made to the managed entities in the persistence context are applied
     * to the database and committed.
     */
    private void commitTransaction() {
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        entityTransaction.commit();
    }

    public <T> T persistEntity(T entity) {
        entityManager.persist(entity);
        commitTransaction();

        return entity;
    }

    public <T> T mergeEntity(T entity) {
        entity = entityManager.merge(entity);
        commitTransaction();

        return entity;
    }

    public <T> void removeEntity(T entity) {
        entityManager.remove(entityManager.merge(entity));
        commitTransaction();
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return entityManager.createQuery(cq).getResultList();
    }

    public <T> int getCount(Class<T> entityClass) {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = entityManager.createQuery(cq);

        return ((Long) q.getSingleResult()).intValue();
    }

/*
    public List<Wine> getWineFindByYear(int year) {
        return entityManager.createNamedQuery("Wine.findByYear", Wine.class).setParameter("year", year).getResultList();
    }

    public List<Wine> getWineFindByCountry(String country) {
        return entityManager.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }

    public List<Wine> getWineFindByVarietal(String varietal) {
        return entityManager.createNamedQuery("Wine.findByVarietal", Wine.class).setParameter("varietal", varietal).getResultList();
    }
*/
}
