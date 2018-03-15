package cdioil.persistence;

import java.io.Serializable;

/**
 *
 * @author António Sousa [1161371]
 */
public interface RepositoryDados<T, K extends Serializable> {

    /**
     * Devolve todas as entidades no repositorio.
     *
     * @return coleção de entidades armazenadas no repositório.
     */
    Iterable<T> findAll();

    /**
     * Devolve uma identidade com o identificador dado.
     *
     * @param id identificador da identidade
     * @return a identidade pretendida se presente, null caso contrário.
     */
    T find(K id);

    /**
     * Adiciona uma entidade ao repositório.
     *
     * @param entidade a entidade a persistir
     * @return entidade persistida.
     */
    T add(T entidade);
    
    /**
     * Atualiza a entidade
     * @param entity T com a entidade a ser atualizada
     * @return T com a entidade alterada, ou null caso tenha ocorrido
     */
    T merge(T entity);
}
