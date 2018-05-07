package cdioil.logger;

/**
 * Interface that holds the file names for all logger files (one for each
 * module)
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public interface LoggerFileNames {
    /**
     * File name for the backoffice module logger.
     */
    public static final String BACKOFFICE_LOGGER_FILE_NAME = "backoffice_logger.xml";
    /**
     * File name for the bootstrap module logger.
     */
    public static final String BOOTSTRAP_LOGGER_FILE_NAME = "bootstrap_logger.xml";
    /**
     * File name for the core module logger.
     */
    public static final String CORE_LOGGER_FILE_NAME = "core_logger.xml";
    /**
     * File name for the framework module logger.
     */
    public static final String FRAMEWORK_LOGGER_FILE_NAME = "framework_logger.xml";
    /**
     * File name for the frontoffice module logger.
     */
    public static final String FRONTOFFICE_LOGGER_FILE_NAME = "frontoffice_logger.xml";
    /**
     * File name for the util module logger.
     */
    public static final String UTIL_LOGGER_FILE_NAME = "util_logger.xml";
}
