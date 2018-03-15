package cdioil.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import cdioil.persistence.RepositoryDados;

/**
 * Classe utilitária abstrata para implementar repositórios. Baseada na classe
 * apresentada nas aulas de EAPLI e desenvolvida pelo docente Paulo Gandra de
 * Sousa.
 *
 * @author António Sousa [1161371]
 * @param <T> tipo de entidades as quais se destina este repositorio
 * @param <K> identificador da entidade
 */
public abstract class RepositorioBaseJPA<T, K extends Serializable> implements RepositoryDados<T, K> {

    //Expressa a dependência de uma unidade de persistencia.
    @PersistenceUnit
    private static EntityManagerFactory factory;
    private EntityManager entityManager;
    private final Class<T> classeEntidade;

    public RepositorioBaseJPA() {
        ParameterizedType genericSuperclass
                = (ParameterizedType) getClass().getGenericSuperclass();
        this.classeEntidade
                = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        entityManagerFactory();
    }

    /**
     * Método para que as classes possam definir a unidade de persistencia.
     *
     * @return o nome da unidade de persistência.
     */
    protected abstract String nomeUnidadePersistencia();

    /**
     * Cria uma EntityManagerFactory se ainda não existir uma. Utiliza o padrão
     * Singleton para garantir que apenas uma factory existe.
     *
     * @return a nova factory.
     */
    protected EntityManagerFactory entityManagerFactory() {
        if (factory == null) {
            System.out.println(nomeUnidadePersistencia());
            factory = Persistence
                    .createEntityManagerFactory(nomeUnidadePersistencia());
            System.out.println(factory);
        }
        return factory;
    }

    /**
     * Cria um novo entity manager se o atual tiver sido fechado.
     *
     * @return o novo entity manager.
     */
    protected EntityManager entityManager() {

        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    @Override
    public Iterable<T> findAll() {

        Query query = entityManager().createQuery("SELECT e FROM " + classeEntidade.getSimpleName() + " e");

        return query.getResultList();

    }

    @Override
    public T find(K id) {

        return entityManager().find(classeEntidade, id);
    }

    @Override
    public T add(T entidade) {

        if (entidade == null) {
            throw new IllegalArgumentException("A entidade a persistir não pode ser nula");
        }

        EntityManager em = entityManager();
        EntityTransaction transacao = em.getTransaction();

        transacao.begin();
        em.persist(entidade);
        transacao.commit();
        em.close();

        return entidade;
    }

}
