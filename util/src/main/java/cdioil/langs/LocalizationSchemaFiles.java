package cdioil.langs;

import cdioil.files.FilesUtils;

/**
 * Class containing constants referencing schema files used for localization
 * purposes.
 *
 * @author António Sousa [1161371]
 */
public interface LocalizationSchemaFiles {

    /**
     * String XSD schema file's path. The XSD file is used for verifying XML
     * localization files.
     */
    public static final String LOCALIZATION_SCHEMA_PATH = FilesUtils.convertStringToUTF8(
            LocalizationSchemaFiles.class.getClassLoader().getResource("localization/localization_schema.xsd").getFile());

}
