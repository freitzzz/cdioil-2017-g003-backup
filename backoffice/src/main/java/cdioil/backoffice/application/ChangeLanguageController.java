package cdioil.backoffice.application;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.BackOfficeProperties;
import cdioil.langs.Language;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Controller for changing the user interface's language.
 *
 * @author Antonio Sousa
 */
public class ChangeLanguageController {

    
    /**
     * Changes the user interface's language and updates the properties file.
     * @param l new language
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public void changeLanguage(Language l) throws ParserConfigurationException, SAXException, IOException{
        
        Properties properties = new Properties();
        
        try (FileInputStream inputStream = new FileInputStream(BackOfficeProperties.PROPERTIES_FILE_PATH)) {
            properties.load(inputStream);
        }
        
        properties.setProperty("language", l.name());
        
        try (FileOutputStream outputStream = new FileOutputStream(BackOfficeProperties.PROPERTIES_FILE_PATH)) {
            properties.store(outputStream, "Current Language");
        }
        
        BackOfficeLocalizationHandler.getInstance().loadStrings();
    }
    
    
}
