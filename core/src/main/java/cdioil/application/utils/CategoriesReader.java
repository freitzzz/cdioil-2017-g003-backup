package cdioil.application.utils;

import cdioil.domain.MarketStructure;
import java.io.Serializable;

/**
 * Interface for reading categories from a file.
 *
 * @author Rita Gonçalves (1160912)
 */
public interface CategoriesReader extends Serializable{

    /**
     * Imports Categories from a file of any format.
     *
     * @return Market Structure with the Categories that were read
     */
    public abstract MarketStructure readCategories();

    /**
     * Returns the number of Categories in the list of Categories.
     *
     * @return the number of Categories that were read
     */
    public abstract int getNumberOfCategoriesRead();

}
