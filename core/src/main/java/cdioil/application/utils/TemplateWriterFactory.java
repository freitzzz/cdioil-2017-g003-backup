/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Template;
import cdioil.files.CommonFileExtensions;

/**
 * Factory of TemplateWriterFactory.
 *
 * @author Pedro Portela (1150782)
 * @author Ana Guerra (1161191)
 */
public final class TemplateWriterFactory {
   
    /**
     * Hides the default constructor.
     */
    private TemplateWriterFactory() {
    }

    /**
     * Creates an instance of SurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param template Template to export
     *
     * @return the created TemplateWriter. If the file is not valid, returns null.
     */
    public static TemplateWriter create(String filename, Template template) {
        if (filename.endsWith(CommonFileExtensions.XML_EXTENSION)) {
            return new XMLTemplateWriter(filename, template);
        }
        return null;
    }
}
