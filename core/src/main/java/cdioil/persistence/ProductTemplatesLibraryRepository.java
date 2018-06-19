package cdioil.persistence;

import cdioil.domain.ProductTemplatesLibrary;

/**
 * Interface defining the behaviour for all implementations of
 * ProductTemplatesLibraryRepository.
 *
 * @author António Sousa [1161371]
 */
public interface ProductTemplatesLibraryRepository {

    /**
     * Fetches the single instance of a ProductTemplatesLibrary stored in the
     * database.
     *
     * @return persisted ProductTemplatesLibrary
     */
    public ProductTemplatesLibrary findLibrary();

}
