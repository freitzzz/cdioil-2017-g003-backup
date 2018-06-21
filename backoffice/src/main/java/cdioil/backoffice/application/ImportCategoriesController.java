package cdioil.backoffice.application;

import cdioil.application.utils.CategoriesReaderFactory;
import cdioil.application.utils.CategoriesReader;
import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
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
     * @return a the MarketStructure. Null if the filePath is not valid
     */
    public MarketStructure readCategories(String filePath) {
        categoriesReader = CategoriesReaderFactory.create(filePath);
        MarketStructure marketStructure = new MarketStructureRepositoryImpl().findMarketStructure();
        if (categoriesReader != null) {
            MarketStructure me = categoriesReader.readCategories();
            if (me != null) {
                for (Category cat : me.getAllCategories()) {
                    marketStructure.addCategory(cat);
                    if (new MarketStructureRepositoryImpl().merge(marketStructure) != null) {
                        return marketStructure;
                    }
                }
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
