package cdioil.backoffice.application;

import cdioil.application.utils.TemplateWriter;
import cdioil.application.utils.TemplateWriterFactory;
import cdioil.domain.Category;
import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.domain.IndependentTemplatesLibrary;
import cdioil.domain.Product;
import cdioil.domain.ProductTemplatesLibrary;
import cdioil.domain.SurveyItem;
import cdioil.domain.Template;
import cdioil.domain.TemplateGroup;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.ProductTemplatesLibraryRepositoryImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;

/**
 * Controller class for the User Story 238 - Export Template for a CSV, XML or
 * JSON file.
 *
 * @author Pedro Portela (1150782)
 * @author Ana Guerra (1161191)
 */
public class ExportTemplateController {

    /**
     * List with all templates.
     */
    private final List<Template> templates;

    private final Map<Template, List<SurveyItem>> templateItems;

    /**
     * Chosen template by the user.
     */
    private Template template;

    private final Manager manager;

    /**
     * Builds an instance of ExportTemplateController.
     *
     * @param manager currently logged in Manager
     */
    public ExportTemplateController(Manager manager) {
        this.templates = new ArrayList<>();
        this.templateItems = new HashMap<>();
        this.manager = manager;
    }

    /**
     * Returns the list with all available templates.
     *
     * @return
     */
    public List<String> getAllTemplates() {

        List<String> templateStrings = new LinkedList<>();

        CategoryTemplatesLibrary categoryTemplatesLibrary = new CategoryTemplatesLibraryRepositoryImpl().findTemplatesForCategory();

        ProductTemplatesLibrary productTemplatesLibrary = new ProductTemplatesLibraryRepositoryImpl().findLibrary();

        IndependentTemplatesLibrary independentTemplatesLibrary = new IndependentTemplatesLibraryRepositoryImpl().findLibrary();

        //Add all the templates that are mapped to categories associated with the Manager
        for (Map.Entry<Category, TemplateGroup> entry : categoryTemplatesLibrary.getID().entrySet()) {
            if (manager.isAssociatedWithCategory(entry.getKey())) {
                for (Template t : entry.getValue().getTemplates()) {
                    //Add the template and its respective text representation to the lists
                    if (!templates.contains(t)) {
                        templates.add(t);
                        templateStrings.add(t.toString());
                    }

                    //Add to list of items mapped to the template
                    if (!templateItems.containsKey(t)) {
                        templateItems.put(t, new LinkedList<>());
                    }
                    templateItems.get(t).add(entry.getKey());
                }
            }
        }

        //Add all the templates mapped to products
        for (Map.Entry<Product, TemplateGroup> entry : productTemplatesLibrary.getID().entrySet()) {
            for (Template t : entry.getValue().getTemplates()) {
                if (!templates.contains(t)) {
                    templates.add(t);
                    templateStrings.add(t.toString());
                }
                
                //Add to list of items mapped to the template
                if (!templateItems.containsKey(t)) {
                    templateItems.put(t, new LinkedList<>());
                }
                templateItems.get(t).add(entry.getKey());
            }
        }

        for (Template t : independentTemplatesLibrary.getID()) {
            if (templates.contains(t)) {
                templates.add(t);
                templateStrings.add(t.toString());
            }
        }

        return templateStrings;
    }

    /**
     * Returns the chosen template.
     *
     * @param templateID ID of the template
     * @return the template itself. Null if the chosen index is not valid
     */
    public boolean getChosenTemplate(String templateID) {
        try {
            template = templates.get(Integer.parseInt(templateID) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            return false;
        }
        return true;
    }

    /**
     * Exports the template to a file.
     *
     * @param filePath Path where the file will be stored
     * @return true if the template was succesfully exported
     * @throws javax.xml.bind.JAXBException if a JAXB error occurs
     */
    public boolean exportTemplate(String filePath) throws JAXBException {
        TemplateWriter templateWriter = TemplateWriterFactory.create(filePath, template);
        if (templateWriter == null) {
            return false;
        }
        List<SurveyItem> items = templateItems.get(template);
        if (items != null) {
            templateWriter.addSurveyItems(items);
        }
        return templateWriter.write();
    }
}
