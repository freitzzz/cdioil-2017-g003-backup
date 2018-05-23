package cdioil.backoffice.utils;

import cdioil.files.FilesUtils;

/**
 * Static Class containing the file path of the properties file.
 *
 * @author Antonio Sousa
 */
public interface BackOfficeProperties {

    /**
     * BackOffice's Properties File.
     */
    public static final String PROPERTIES_FILE_PATH = FilesUtils.convertStringToUTF8(
            BackOfficeProperties.class.getClassLoader().getResource("backoffice.properties").getFile());
}
