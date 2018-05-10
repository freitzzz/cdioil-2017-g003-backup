package cdioil.backoffice.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static Class containing the file path of the properties file.
 *
 * @author Antonio Sousa
 */
public final class BackOfficeProperties {

    /**
     * BackOffice's Properties File.
     */
    public static final String PROPERTIES_FILE_PATH = BackOfficeProperties.class.getClassLoader().getResource("backoffice.properties").getFile();

    /**
     * This block statically sets the file name to be UTF-8 compliant, however,
     * URLDecoder's decode method throws an UnsupportedEncodingException that
     * needs to be caught, which can't be done in an interface, so if it fails
     * the value of PROPERTIES_FILE_PATH is reverted to the original value.
     * Unfortunately, the field also needs to be final, so Reflection is used to
     * be able to change the field.
     */
    static {

        try {

            Field propertiesFileField = BackOfficeProperties.class.getField("PROPERTIES_FILE_PATH");

            Field modifiersField = Field.class.getDeclaredField("modifiers");

            modifiersField.setAccessible(true);

            //Disable the field's final modifier
            modifiersField.setInt(propertiesFileField, propertiesFileField.getModifiers() & ~Modifier.FINAL);

            propertiesFileField.set(null, URLDecoder.decode(BackOfficeProperties.class.getClassLoader().getResource("backoffice.properties").getFile(), "UTF-8"));

            //Re-enable the final modifier
            modifiersField.setInt(propertiesFileField, propertiesFileField.getModifiers() & Modifier.FINAL);

        } catch (NoSuchFieldException | SecurityException | UnsupportedEncodingException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(BackOfficeProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Private constructor for hiding the implicit public one.
     */
    private BackOfficeProperties() {
    }
}
