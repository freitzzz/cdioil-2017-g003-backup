/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author João
 */
public class Reader {

    /**
     * String with the identifier to category.
     */
    public static final String CATEGORY = "categoria";
    /**
     * String with the identifier to list of the scats.
     */
    public static final String LIST_SCAT = "lista_scat";
    /**
     * String with the identifier to lists of the ubs.
     */
    public static final String LIST_UB = "lista_ub";
    /**
     * String with the identifier to lists of the categories.
     */
    public static final String LIST_CATEGORIES = "lista_categorias";
    /**
     * String with the CAT identifier.
     */
    public static final String DEC_CAT = "descritivo_cat";
    /**
     * String with the SCAT identifier.
     */
    public static final String DEC_SCAT = "descritivo_scat";
    /**
     * String with the ID identifier.
     */
    public static final String ID = "id";
    /**
     * String with the DC identifier.
     */
    public static final String DC = "DC";
    /**
     * String with the UN identifier.
     */
    public static final String UN = "UN";
    /**
     * String with the CAT identifier.
     */
    public static final String CAT = "CAT";
    /**
     * String with the SCAT identifier.
     */
    public static final String SCAT = "SCAT";
    /**
     * String with the UB identifier.
     */
    public static final String UB = "UB";

    /**
     * Converts a document to a string
     *
     * @param doc document to convert
     * @return String containing the document's information
     */
    public String documentToString(Document doc) throws TransformerException {

        // Create dom source for the document
        DOMSource domSource = new DOMSource(doc);

        // Create a string writer
        StringWriter stringWriter = new StringWriter();

        // Create the result stream for the transform
        StreamResult result = new StreamResult(stringWriter);

        // Create a Transformer to serialize the document
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");

        // Transform the document to the result stream
        transformer.transform(domSource, result);
        return stringWriter.toString();
    }
}
