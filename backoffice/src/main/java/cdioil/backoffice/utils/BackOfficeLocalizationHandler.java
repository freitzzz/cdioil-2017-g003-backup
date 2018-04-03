package cdioil.backoffice.utils;

import cdioil.langs.Language;
import cdioil.langs.LocalizationParserXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Singleton class that handles the BackOffice's localization.
 *
 * @author Antonio Sousa
 */
public class BackOfficeLocalizationHandler {

    private static final Map<Language, File> LANGUAGE_FILES_MAP;

    static {
        
        LANGUAGE_FILES_MAP = new HashMap<>();
        
        //Instantiate Map and convert URLs to UTF-8
        try {
            LANGUAGE_FILES_MAP.put(Language.pt_PT,
                    new File(URLDecoder.decode(BackOfficeLocalizationHandler.class.getClassLoader()
                            .getResource("localization/backoffice_pt_PT.xml").getFile(), "UTF-8")));
            
            LANGUAGE_FILES_MAP.put(Language.en_US,
                    new File(URLDecoder.decode(BackOfficeLocalizationHandler.class.getClassLoader()
                            .getResource("localization/backoffice_en_US.xml").getFile(), "UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BackOfficeLocalizationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reference to the current instance.
     */
    private static BackOfficeLocalizationHandler instance;

    /**
     * Map with the localized strings and respective identifiers.
     */
    private Map<String, String> messagesMap;

    /**
     * Private constructor for hiding the implicit public one.
     */
    private BackOfficeLocalizationHandler() {
    }

    /**
     * Retrieve the single instance BackOfficeLocalizationHandler.
     *
     * @return BackOfficeLocalizationHandler instance
     */
    public static BackOfficeLocalizationHandler getInstance() {
        if (instance == null) {
            instance = new BackOfficeLocalizationHandler();
            instance.messagesMap = new HashMap<>();
        }

        return instance;
    }

    /**
     * Loads localized strings of the currently specified language in the
     * properties file.
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void loadStrings() throws ParserConfigurationException, SAXException, IOException {

        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(BackOfficeProperties.PROPERTIES_FILE_PATH);
        
        properties.load(inputStream);
        inputStream.close();

        Language definedLanguage = Language.valueOf(properties.getProperty("language"));

        if (definedLanguage != null) {

            File file = LANGUAGE_FILES_MAP.get(definedLanguage);

            if (file != null) {

                messagesMap = LocalizationParserXML.parseFile(file);
            }
        }
    }

    /**
     * Fetches localized string with a given id.
     *
     * @param messageID string identifier
     * @return localized string
     */
    public String getMessageValue(String messageID) {

        return messagesMap.get(messageID);
    }

}
