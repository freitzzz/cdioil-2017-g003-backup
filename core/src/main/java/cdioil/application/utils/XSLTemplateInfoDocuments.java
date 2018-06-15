/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.files.FileReader;
import cdioil.files.FilesUtils;
import java.io.File;

/**
 * Interface that represents the XSL documents for the template transformation.
 *
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 *
 * @since Version 7.0 of FeedbackMonkey
 */
public interface XSLTemplateInfoDocuments {

    /**
     * Constant that represents the XSL document containing the transformation being applied
     * to a XML file to transform it into a JSON document containing the template.
     */
    public static final String JSON_TEMPLATE_XSLT
            = FilesUtils.listAsString(FileReader.readFile(
                    new File(XSLTemplateInfoDocuments.class.getClassLoader().
                            getResource("xsl/JSONTemplateXSLT.xsl").getFile())));

    /**
     * Constant that represents the XSL document containing the transformation being applied
     * to a XML file to transform it into a CSV document containing the template.
     */
    public static final String CSV_TEMPLATE_XSLT
            = FilesUtils.listAsString(FileReader.readFile(
                    new File(XSLTemplateInfoDocuments.class.getClassLoader().
                            getResource("xsl/CSVTemplateXSLT.xsl").getFile())));
}
