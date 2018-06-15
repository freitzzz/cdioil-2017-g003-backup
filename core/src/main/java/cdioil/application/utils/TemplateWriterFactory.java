package cdioil.application.utils;

import cdioil.domain.Template;
import cdioil.files.CommonFileExtensions;

/**
 * Factory of TemplateWriterFactory.
 *
 * @author <a href="1150782@isep.ipp.pt">Pedro Portela</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
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
     * @param filePath Path of the file to export
     * @param template Template to export
     *
     * @return the created TemplateWriter if the file is valid. Otherwise, returns null.
     */
    public static TemplateWriter create(String filePath, Template template) {
        if (filePath.endsWith(CommonFileExtensions.XML_EXTENSION)) {
            return new XMLTemplateWriter(filePath, template);
        } else if (filePath.endsWith(CommonFileExtensions.CSV_EXTENSION)) {
            return new CSVTemplateWriter(filePath, template);
        } else if (filePath.endsWith(CommonFileExtensions.JSON_EXTENSION)) {
            return new JSONTemplateWriter(filePath, template);
        }
        return null;
    }
}
