package cdioil.application.utils;

/**
 * UsersReader Factory
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class UsersReaderFactory {
    /**
     * Constant that represents a plain file extension
     */
    private static final String PLAIN_TEXT_EXTENSION=".txt";
    /**
     * Constant that represents a XML file extension
     */
    private static final String XML_EXTENSION=".xml";
    /**
     * Constant that represents a CSV file extension
     */
    private static final String CSV_EXTENSION=".csv";
    /**
     * Builds a new UsersReader with a certain file path
     * @param file String with the file path
     * @return UsersReader with the respective reader and parser or null if the 
     * file extension is not allowed
     */
    public static UsersReader create(String file){
        if(file.endsWith(CSV_EXTENSION)){
            return new CSVUsersReader(file);
        }
        return null;
    }
    /**
     * Hides default constructor
     */
    private UsersReaderFactory(){}
}
