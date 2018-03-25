package cdioil.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.Iterator;

/**
 * Abstract class used for implementing repositories. Based on the Class
 * presented in the EAPLI course unit and developed by Professor Paulo Gandra de
 * Sousa.
 *
 * @author António Sousa [1161371]
 * @param <T> Type of entity to be stored in the repository
 * @param <K> The entity's type of identifier
 */
public abstract class BaseJPARepository<T, K extends Serializable> implements DataRepository<T, K> {

    //Depends on a persistence unit.
    @PersistenceUnit
    private static EntityManagerFactory factory;
    private EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseJPARepository() {
        ParameterizedType genericSuperclass
                = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass
                = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        entityManagerFactory();
    }

    /**
     * Method for setting the persistence unit.
     *
     * @return the persistence unit's name.
     */
    protected abstract String persistenceUnitName();

    /**
     * Instantiates an EntityManagerFactory if there is none yet. Uses the
     * Singleton design pattern for ensuring only factory is instantiated.
     *
     * @return the new factory.
     */
    protected EntityManagerFactory entityManagerFactory() {
        if (factory == null) {
            factory = Persistence
                    .createEntityManagerFactory(persistenceUnitName());
        }
        return factory;
    }

    /**
     * Creates a new EntityManager if the current one has been closed.
     *
     * @return the new entity manager.
     */
    protected EntityManager entityManager() {

        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    @Override
    public Iterable<T> findAll() {

        Query query = entityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e");

        return query.getResultList();

    }

    @Override
    public T find(K id) {

        return entityManager().find(entityClass, id);
    }

    @Override
    public T add(T entity) {

        if (entity == null) {
            throw new IllegalArgumentException("A entidade a persistir não pode ser nula");
        }

        EntityManager em = entityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(entity);
        transaction.commit();
        em.close();

        return entity;
    }

    @Override
    public T merge(T entity) {
        if (entity == null) {
            return null;
        }
        EntityManager em = entityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
        em.close();
        return entity;
    }

    /**
     * <b>SOLUCAO TEMPORARIA</b>
     * <br>Não me julguem
     *
     * @param entity
     * @return
     */
    public T getEntity(T entity) {
        if (entity == null) {
            return null;
        }
        Iterator<T> iteratorEntities = findAll().iterator();
        while (iteratorEntities.hasNext()) {
            if (iteratorEntities.next().equals(entity)) {
                return entity;
            }
        }
        return null;
    }

}
