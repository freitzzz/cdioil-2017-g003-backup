package cdioil.application.utils;

import cdioil.domain.SurveyItem;
import cdioil.domain.Template;
import cdioil.files.FileWriter;
import cdioil.xsl.XSLTransformer;
import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 * Exports a template to a CSV file.
 *
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 *
 * @since Version 7.0 of FeedbackMonkey
 */
public class CSVTemplateWriter implements TemplateWriter {

    /**
     * Path of the file to export.
     */
    private final String filePath;
    
    private final XMLTemplateWriter xmlWriter;

    /**
     * Creates a new JSONTemplateWriter.
     *
     * @param filePath Path of the file to export
     * @param template Template to export
     */
    public CSVTemplateWriter(String filePath, Template template) {
        this.filePath = filePath;
        this.xmlWriter = new XMLTemplateWriter(filePath, template);
    }

    @Override
    public boolean write() throws JAXBException {
        return FileWriter.writeFile(new File(filePath), XSLTransformer.
                create(XSLTemplateInfoDocuments.CSV_TEMPLATE_XSLT).
                transform(xmlWriter.getXMLAsString()));
    }

    @Override
    public boolean addSurveyItems(List<SurveyItem> surveyItems) {
        return xmlWriter.addSurveyItems(surveyItems);
    }

}
