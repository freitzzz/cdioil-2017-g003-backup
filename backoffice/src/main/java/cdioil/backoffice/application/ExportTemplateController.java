/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.application.utils.TemplateWriter;
import cdioil.application.utils.TemplateWriterFactory;
import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.domain.Template;
import cdioil.domain.TemplateGroup;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the User Story 238 - Export Template for a CSV, XML or JSON file.
 *
 * @author Pedro Portela (1150782)
 * @author Ana Guerra (1161191)
 */
public class ExportTemplateController {

    /**
     * List with all templates.
     */
    private final List<Template> templates;

    /**
     * Chosen template by the user.
     */
    private Template template;

    /**
     * Builds an instance of ExportTemplateController.
     */
    public ExportTemplateController() {
        templates = new ArrayList<>();
    }

    /**
     * Returns the list with all available templates.
     *
     * @return all templates
     */
    public List<Template> getAllTemplates() {
        Iterable<CategoryTemplatesLibrary> templateLibrary = new CategoryTemplatesLibraryRepositoryImpl().findAll();
        for (CategoryTemplatesLibrary categoryTemplatesLibrary : templateLibrary) {
            for (TemplateGroup ola : categoryTemplatesLibrary.getLibrary().values()) {
                templates.addAll(ola.getTemplates());
            }
        }
        return templates;
    }

    /**
     * Returns the chosen template.
     *
     * @param templateID ID of the template
     * @return the template itself. Null if the chosen index is not valid
     */
    public Template getChosenTemplate(String templateID) {
        try {
            Template chosenOne = templates.get(Integer.parseInt(templateID) - 1);
            template = chosenOne;
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            return null;
        }
        return template;
    }

    /**
     * Exports the template to a file.
     *
     * @param filePath Path where the file will be stored
     * @return
     */
    public boolean exportTemplate(String filePath) {
        TemplateWriter statsWriter = TemplateWriterFactory.create(filePath, template);
        if (statsWriter == null) {
            return false;
        }
        return statsWriter.write();
    }
}
