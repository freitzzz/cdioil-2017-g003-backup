package cdioil.persistence;

import cdioil.domain.IndependentTemplatesLibrary;

/**
 * Interface defining the behaviour for all implementations of
 * IndependentTemplatesLibraryRepository.
 *
 * @author António Sousa [1161371]
 */
public interface IndependentTemplatesLibraryRepository {

    /**
     * Fetches the single instance of a IndependentTemplatesLibrary stored in
     * the database.
     *
     * @return persisted IndependentTemplatesLibrary
     */
    public IndependentTemplatesLibrary findLibrary();
}
