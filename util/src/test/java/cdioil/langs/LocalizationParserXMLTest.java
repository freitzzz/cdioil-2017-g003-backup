package cdioil.langs;

import java.io.File;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antonio Sousa
 */
public class LocalizationParserXMLTest {
    
    public LocalizationParserXMLTest() {
    }

    /**
     * Test of parseFile method, of class LocalizationParserXML.
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("parseFile");
        File file = new File("../backoffice/src/main/resources/localization/backoffice_pt_PT.xml");
        
        Map<String, String> result = LocalizationParserXML.parseFile(file);
        
        System.out.println("Number of messages parsed: " + result.size());
        
        result.get("info_welcome");
        
        assertEquals("Bem-vindo ao BackOffice\n", result.get("info_welcome"));
    }
    
}
