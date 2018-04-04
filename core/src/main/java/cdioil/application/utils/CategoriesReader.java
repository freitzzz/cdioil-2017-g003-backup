/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.MarketStructure;

/**
 * Interface for reading categories from a file.
 *
 * @author Rita Gonçalves (1160912)
 */
public interface CategoriesReader {

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
