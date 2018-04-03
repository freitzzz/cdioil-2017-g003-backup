package cdioil.backoffice.utils;

import java.io.File;

/**
 *
 * @author Antonio Sousa
 */
public interface BackOfficeProperties {

    /**
     * BackOffice's Properties File.
     */
    public final static File PROPERTIESFILE = new File(BackOfficeProperties.class.getClassLoader().getResource("backoffice.properties").getFile());
}
