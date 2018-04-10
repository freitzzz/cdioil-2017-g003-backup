package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;

/**
 * UsersReader Factory
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class UsersReaderFactory {
    
    /**
     * Builds a new UsersReader with a certain file path
     * @param file String with the file path
     * @return UsersReader with the respective reader and parser or null if the 
     * file extension is not allowed
     */
    public static UsersReader create(String file){
        if(file.endsWith(CommonFileExtensions.CSV_EXTENSION)){
            return new CSVUsersReader(file);
        }
        return null;
    }
    /**
     * Hides default constructor
     */
    private UsersReaderFactory(){}
}
