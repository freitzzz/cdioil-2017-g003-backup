/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application;

import cdioil.application.utils.CategoriesReaderFactory;
import cdioil.application.utils.CategoriesReader;
import cdioil.domain.MarketStructure;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

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
    public MarketStructure readCategories(String file) {
        CategoriesReader categoriesReader = CategoriesReaderFactory.create(file);

        if (categoriesReader != null){
            MarketStructure em = categoriesReader.readCategories();
            if (new MarketStructureRepositoryImpl().merge(em) != null) return em;
        }
        return null;
    }
}
