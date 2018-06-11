package cdioil.xsl;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests relatively to XSLTransformer class that applies XSL Transformations 
 * to XML documents
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class XSLTransformerTest {
    
    public XSLTransformerTest() {}

    /**
     * Test of create method, of class XSLTransformer.
     */
    @Test(expected = NullPointerException.class)
    public void testCreate_Invalid_File() {
        System.out.println("createInvalidTransformationDocumentAsFile");
        File xslDocument = null;
        assertNull("An exception should be thrown since the file is null, so the XSL document is invalid"
                ,XSLTransformer.create(xslDocument));
    }

    /**
     * Test of create method, of class XSLTransformer.
     */
    @Test(expected = IllegalStateException.class)
    public void testCreate_Invalid_String() {
        System.out.println("createInvalidTransformationDocumentAsString");
        assertNull("An exception should be thrown since the XSL document is invalid"
                ,XSLTransformer.create(getInvalidXSLDocument()));
    }
    
    /**
     * Test of create method, of class XSLTransformer.
     */
    public void testCreate_String() {
        System.out.println("createValidTransformationDocumentAsString");
        assertNotNull("An exception should be thrown since the XSL document is invalid"
                ,XSLTransformer.create(getValidXSLDocument()));
    }

    /**
     * Test of transform method, of class XSLTransformer.
     */
    @Test(expected = NullPointerException.class)
    public void testTransform_Invalid_File() {
        System.out.println("transformInvalidFile");
        File invalidFile=null;
        assertNull("An exception should be thrown since the XML document being transformed is invalid"
                ,createValidXSLTransformer().transform(invalidFile));
    }
    
    /**
     * Test of transform method, of class XSLTransformer.
     */
    @Test(expected = IllegalStateException.class)
    public void testTransform_Invalid_String() {
        System.out.println("transformInvalidString");
        assertNull("An exception should be thrown since the XML document being transformed is invalid"
                ,createValidXSLTransformer().transform(getInvalidXMLDocument()));
    }

    /**
     * Test of transform method, of class XSLTransformer.
     */
    @Test
    public void testTransform_Valid_String() {
        System.out.println("transform");
        String validTransformedXMLDocument="CHVRCHES is a very cool band ! ! ! :)";
        assertEquals("The condition should be succesful since the transformation applied to the XML "
                + "document was succesful, and the generated document is correct"
                ,createValidXSLTransformer().transform(getValidXMLDocument())
                        .trim()
                ,validTransformedXMLDocument);
    }
    
    /**
     * Method that returns a valid XSLTransformer
     * @return XSLTransformer with a vaid XSL Transformer
     */
    private XSLTransformer createValidXSLTransformer(){
        return XSLTransformer.create(getValidXSLDocument());
    }
    /**
     * Method that returns a valid XSL document
     * @return String with a valid XSL document
     */
    private String getValidXSLDocument(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"2.0\">\n"
                + "<xsl:output method=\"text\" indent=\"no\"/>\n"
                + "<xsl:template match=\"CHVRCHES\"><xsl:value-of select=\".\"/></xsl:template>\n"
                + "</xsl:stylesheet>";
    }
    /**
     * Method that returns an invalid XSL document
     * @return String with an invalid XSL document
     */
    private String getInvalidXSLDocument(){
        return "This XSL document is definetly not valid!!!";
    }
    /**
     * Method that returns a valid XML document
     * @return String with a valid XML document
     */
    private String getValidXMLDocument(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                + "<CHVRCHES>\n"
                + "CHVRCHES is a very cool band ! ! ! :)\n"
                + "</CHVRCHES>";
    }
    /**
     * Method that returns an invalid XML document
     * @return String with an invalid XML document
     */
    private String getInvalidXMLDocument(){
        return "Lil DMT sipp's a new kind of Lean";
    }
}
