package cdioil.langs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Static class for handling the parsing of XML localization files.
 * @author Antonio Sousa
 */
public class LocalizationParserXML {

    /**
     * Private constructor for hiding the implicit public one.
     */
    private LocalizationParserXML(){
        
    }
    
    /**
     * Parses the XML localization file.
     * @param file XML file
     * @return Map with localized strings and their respective identifier
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public static Map<String, String> parseFile(File file) throws ParserConfigurationException, SAXException, IOException {

        Map<String, String> languageStringsMap = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);

        NodeList messages = document.getElementsByTagName("Message");

        for (int i = 0; i < messages.getLength(); i++) {

            Element element = (Element) messages.item(i);

            String messageId = element.getAttribute("messageId");

            String value = element.getAttribute("value");

            languageStringsMap.put(messageId, value);
        }

        return languageStringsMap;
    }

}