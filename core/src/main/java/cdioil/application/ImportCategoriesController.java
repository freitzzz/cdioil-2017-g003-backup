/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application;

import cdioil.domain.Category;
import cdioil.application.utils.CategoriesReaderFactory;

import java.util.List;
import cdioil.application.utils.CategoriesReader;

/**
 * Controller class for the User Story 201 - Import Categories from a File.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ImportCategoriesController {

    /**
     * Imports a list of Categories from a file.
     *
     * @param file Path of the file
     * @return a list with the readCategories Categories. Null if the file is not valid
     */
    public List<Category> readCategories(String file) {
        CategoriesReader categoriesReader = CategoriesReaderFactory.create(file);

        return categoriesReader != null ? categoriesReader.readCategories() : null;
    }
}
