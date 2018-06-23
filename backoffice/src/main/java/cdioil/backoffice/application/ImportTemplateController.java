package cdioil.backoffice.application;

import cdioil.application.utils.TemplateReader;
import cdioil.application.utils.TemplateReaderFactory;
import cdioil.domain.Category;
import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.domain.IndependentTemplatesLibrary;
import cdioil.domain.Product;
import cdioil.domain.ProductTemplatesLibrary;
import cdioil.domain.SurveyItem;
import cdioil.domain.SurveyItemType;
import cdioil.domain.Template;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.ProductRepositoryImpl;
import cdioil.persistence.impl.ProductTemplatesLibraryRepositoryImpl;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Controller for US-236 (import template script from XML file).
 *
 * @author António Sousa [1161371]
 */
public class ImportTemplateController {

    /**
     * Manager currently logged in.
     */
    private final Manager manager;

    /**
     * Instantiates the controller.
     *
     * @param manager currently logged in manager
     */
    public ImportTemplateController(Manager manager) {
        this.manager = manager;
    }

    /**
     * Creates a template from a given file.
     *
     * @param filePath
     * @return true - if a template was created successfully<p>
     * false - otherwise
     * @throws java.io.IOException
     */
    public boolean importTemplate(String filePath) throws IOException {

        TemplateReader templateReader = TemplateReaderFactory.create(filePath);

        List<String> questionIDList = templateReader.getDatabaseQuestionIDList();

        Map<SurveyItemType, List<String>> surveyItemIdentifiers = templateReader.getSurveyItemIdentifiers();

        //If no item identifiers are present, use the independent templates library
        if (surveyItemIdentifiers.isEmpty()) {

            IndependentTemplatesLibraryRepositoryImpl independentTemplatesLibraryRepo = new IndependentTemplatesLibraryRepositoryImpl();

            IndependentTemplatesLibrary independentTemplatesLib = independentTemplatesLibraryRepo.findLibrary();

//            //add fetching by questionID
//            for (String questionID : questionIDList) {
//                //templateReader.addDatabaseQuestion(q);
//            }
            Template template = templateReader.createTemplate();

            if (!independentTemplatesLib.addTemplate(template)) {
                return false;
            }

            return independentTemplatesLibraryRepo.merge(independentTemplatesLib) != null;

        } else {

            List<SurveyItem> associatedItems = new LinkedList<>();

            MarketStructureRepositoryImpl marketStructureRepo = new MarketStructureRepositoryImpl();
            ProductRepositoryImpl productRepo = new ProductRepositoryImpl();

            for (Map.Entry<SurveyItemType, List<String>> entry : surveyItemIdentifiers.entrySet()) {

                SurveyItemType itemType = entry.getKey();

                List<String> itemIdentifiers = entry.getValue();

                if (itemType == SurveyItemType.CATEGORY) {

                    for (String path : itemIdentifiers) {
                        Category c = marketStructureRepo.findCategoryByPath(path);

                        if (c == null) {
                            return false;
                        }

                        if (!manager.isAssociatedWithCategory(c)) {
                            return false;
                        }
                        associatedItems.add(c);
                    }
                } else if (itemType == SurveyItemType.PRODUCT) {

                    for (String sku : itemIdentifiers) {

                        Product p = productRepo.getProductBySKU(sku);

                        if (p == null) {
                            return false;
                        }

                        associatedItems.add(p);
                    }
                }
            }

//            //add fetching by questionID
//            for (String questionID : questionIDList) {
//                //templateReader.addDatabaseQuestion(q);
//            }
            Template template = templateReader.createTemplate();

            //Note: This solution is not ideal since the libraries need to be completely loaded
            CategoryTemplatesLibraryRepositoryImpl catTempLibRepo = new CategoryTemplatesLibraryRepositoryImpl();
            CategoryTemplatesLibrary catTempLib = catTempLibRepo.findTemplatesForCategory();

            ProductTemplatesLibraryRepositoryImpl prodTempLibRepo = new ProductTemplatesLibraryRepositoryImpl();
            ProductTemplatesLibrary prodTempLib = prodTempLibRepo.findLibrary();

            for (SurveyItem surveyItem : associatedItems) {

                boolean added = false;

                if (surveyItem instanceof Category) {

                    //If the category already has an entry in the library then it won't be added
                    catTempLib.addCategory((Category) surveyItem);

                    added = catTempLib.addTemplate((Category) surveyItem, template);

                } else if (surveyItem instanceof Product) {

                    //If the product already has an entry in the library then it won't be added
                    prodTempLib.addProduct((Product) surveyItem);

                    added = prodTempLib.addTemplate((Product) surveyItem, template);
                }

                if (!added) {
                    return false;
                }
            }

            return prodTempLibRepo.merge(prodTempLib) != null && catTempLibRepo.merge(catTempLib) != null;
        }
    }

}
