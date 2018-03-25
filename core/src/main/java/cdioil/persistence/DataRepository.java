package cdioil.persistence;

import java.io.Serializable;

/**
 *
 * @author António Sousa [1161371]
 * @param <T> Type of entity to be stored in the repository
 * @param <K> The entity's type of identifier
 */
public interface DataRepository<T, K extends Serializable> {

    /**
     * Retrieves all the entities in the repository.
     *
     * @return collection of entities stored in the repository
     */
    Iterable<T> findAll();

    /**
     * Returns the entity with the given id.
     *
     * @param id the entity with the given id
     * @return the stored entity, if found, null otherwise
     */
    T find(K id);

    /**
     * Adds an entity to the repository.
     *
     * @param entity entity to persist
     * @return persisted entity
     */
    T add(T entity);
    
    /**
     * Updates the entity
     * @param entity entity to update
     * @return the updated entity, or null if an error occured
     */
    T merge(T entity);
}
