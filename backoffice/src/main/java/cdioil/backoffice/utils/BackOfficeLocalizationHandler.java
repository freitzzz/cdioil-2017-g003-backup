package cdioil.backoffice.utils;

import cdioil.langs.Language;
import cdioil.langs.LocalizationParserXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
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

        LANGUAGE_FILES_MAP.put(Language.pt_PT,
                new File(BackOfficeLocalizationHandler.class.getClassLoader()
                        .getResource("localization/backoffice_pt_PT.xml").getFile()));
        LANGUAGE_FILES_MAP.put(Language.en_US,
                new File(BackOfficeLocalizationHandler.class.getClassLoader()
                        .getResource("localization/backoffice_en_US.xml").getFile()));
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
        FileInputStream inputStream = new FileInputStream(BackOfficeProperties.PROPERTIESFILE);
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

        if (messagesMap == null) {
            return null;
        }

        return messagesMap.get(messageID);
    }

}
