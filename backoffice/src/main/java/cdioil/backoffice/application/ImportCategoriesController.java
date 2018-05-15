package cdioil.backoffice.application;

import cdioil.application.utils.CategoriesReaderFactory;
import cdioil.application.utils.CategoriesReader;
import cdioil.domain.MarketStructure;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.io.File;
import java.io.Serializable;

/**
 * Controller class for the User Story 201 - Import Categories from a File.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ImportCategoriesController implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 8L;

    /**
     * Instance of CategoriesReader to read the file.
     */
    private CategoriesReader categoriesReader;

    /**
     * Imports a list of Categories from a filePath.
     *
     * @param filePath Path of the filePath
     * @return a list with the readCategories Categories. Null if the filePath
     * is not valid
     */
    public MarketStructure readCategories(String filePath) {
        categoriesReader = CategoriesReaderFactory.create(new File(filePath));

        if (categoriesReader != null) {
            MarketStructure em = categoriesReader.readCategories();
            if (new MarketStructureRepositoryImpl().merge(em) != null) {
                return em;
            }
        }
        return null;
    }

    /**
     * Returns the number of Categories in the list of Categories.
     *
     * @return the number of Categories that were read
     */
    public int getNumberOfCategoriesRead() {
        return categoriesReader.getNumberOfCategoriesRead();
    }
}
