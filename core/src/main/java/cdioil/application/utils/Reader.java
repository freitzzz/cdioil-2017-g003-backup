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
