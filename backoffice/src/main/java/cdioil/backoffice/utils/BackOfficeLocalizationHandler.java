package cdioil.backoffice.utils;

import cdioil.files.FilesUtils;
import cdioil.langs.Language;
import cdioil.langs.LocalizationParserXML;
import cdioil.langs.LocalizationSchemaFiles;
import cdioil.langs.LocalizationValidatorXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Singleton class that handles the BackOffice's localization.
 *
 * @author Antonio Sousa
 */
public class BackOfficeLocalizationHandler {

    /**
     * Schema file (XSD) used for validating XML localization files.
     */
    private static final File SCHEMA_FILE = new File(LocalizationSchemaFiles.LOCALIZATION_SCHEMA_PATH);
    /**
     * Map associating Languages to XML localization files.
     */
    private static final Map<Language, File> LANGUAGE_FILES_MAP;

    static {

        LANGUAGE_FILES_MAP = new EnumMap<>(Language.class);

        //Instantiate Map and Schema file and convert URLs to UTF-8F
        LANGUAGE_FILES_MAP.put(Language.PT,
                new File(FilesUtils.convertStringToUTF8(
                        BackOfficeLocalizationHandler.class.getClassLoader().
                                getResource("localization/backoffice_pt_PT.xml").getFile())));

        LANGUAGE_FILES_MAP.put(Language.EN_US,
                new File(FilesUtils.convertStringToUTF8(
                        BackOfficeLocalizationHandler.class.getClassLoader().
                                getResource("localization/backoffice_en_US.xml").getFile())));
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
     * @throws IOException
     */
    public void loadStrings() throws IOException {

        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(BackOfficeProperties.PROPERTIES_FILE_PATH)) {
            properties.load(inputStream);
        }

        Language definedLanguage = Language.valueOf(properties.getProperty("language"));

        if (definedLanguage != null) {

            File xmlFile = LANGUAGE_FILES_MAP.get(definedLanguage);

            if (xmlFile != null) {

                boolean isValid = LocalizationValidatorXML.validateFile(SCHEMA_FILE, xmlFile);

                if (isValid) {

                    messagesMap = LocalizationParserXML.parseFile(xmlFile);
                }
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
