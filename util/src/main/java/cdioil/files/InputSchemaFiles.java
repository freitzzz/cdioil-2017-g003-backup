/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.files;

/**
 * Class containing constants referencing schema files used for validation
 * purposes.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public interface InputSchemaFiles {

    /**
     * String XSD schema file's path. The XSD file is used for verifying XML files for importing independent questions.
     */
    public static final String LOCALIZATION_SCHEMA_PATH_INDEPENDENT_QUESTIONS = FilesUtils.convertStringToUTF8(
            InputSchemaFiles.class.getClassLoader().getResource("xsd/import_independent_questions_schema.xsd").getFile());

    /**
     * String XSD schema file's path. The XSD file is used for verifying XML files for importing products.
     */
    public static final String LOCALIZATION_SCHEMA_PATH = FilesUtils.convertStringToUTF8(
            InputSchemaFiles.class.getClassLoader().getResource("xsd/import_products_schema.xsd").getFile());
    /**
     * String XSD schema file's path. The XSD file is used for verifying XML files for importing category questions.
     */
    public static final String LOCALIZATION_SCHEMA_PATH_CATEGORY_QUESTIONS = FilesUtils.convertStringToUTF8(
            InputSchemaFiles.class.getClassLoader().getResource("xsd/import_category_questions_schema.xsd").getFile());
    /**
     * String XSD schema file's path. The XSD file is used for verifying XML files for importing category questions.
     */
    public static final String LOCALIZATION_SCHEMA_PATH_CATEGORY = FilesUtils.convertStringToUTF8(
            InputSchemaFiles.class.getClassLoader().getResource("xsd/import_category_schema.xsd").getFile());

}
