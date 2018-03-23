/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;
        
import java.util.List;
import cdioil.domain.Category;

/**
 * Interface for reading categories from a file.
 *
 * @author Rita Gonçalves (1160912)
 */
public interface CategoriesReader {

    /**
     * Imports Categories from a file of any format.
     *
     * @return List with the Categories that were read
     */
    public abstract List<Category> readCategories();
}
