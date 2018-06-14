package cdioil.application.utils;

/**
 * Utilitary class that writes a template's information into a file.
 *
 * @author Pedro Portela (1150782)
 * @author Ana Guerra (1161191)
 */
public interface TemplateWriter {

    /**
     * Exports the information about a template to a file of any supported format.
     *
     * @return true, if the file is successfully exported. Otherwise, returns false
     */
    public abstract boolean write();
}
