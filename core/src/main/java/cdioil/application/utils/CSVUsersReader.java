package cdioil.application.utils;

import cdioil.files.FileReader;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.SystemUser;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Utilitary class that reads and parsers users contained on a CSV file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class CSVUsersReader implements UsersReader{
    /**
     * Constant that represents the identifier of the <b>email</b> column on the CSV file
     */
    private static final String EMAIL_COLUMN_IDENTIFIER="EMAIL";
    /**
     * Constant that represents the identifier of the <b>name</b> column on the CSV file
     */
    private static final String NAME_COLUMN_IDENTIFIER="NOME";
    /**
     * Constant that represents the identifier of the <b>subname</b> column on the CSV file
     */
    private static final String SUBNAME_COLUMN_IDENTIFIER="APELIDO";
    /**
     * Constant that represents the identifier of the CSV file delimiter
     */
    private static final String DELIMITER_IDENTIFIER=";";
    /**
     * Constant that represents the number of the CSV line that contains the column identifiers 
     */
    private static final int INDENTIFIER_LINE=0;
    /**
     * Constant that represents the number of identifiers/columns that the CSV file contains
     */
    private static final int NUMBER_OF_IDENITIFIERS=3;
    /**
     * File with the file to be read
     */
    private final File file;
    /**
     * Integer with the number of the <b>email</b> column
     */
    private int emailIdentifier;
    /**
     * Integer with the number of the <b>name</b> column
     */
    private int nameIdentifier;
    /**
     * Integer with the number of the <b>subname</b> column
     */
    private int subnameIdentifier;
    /**
     * Builds a new CSVUsersReader with the file that contains the users to be read
     * @param filename String with the file path that contains the users to be read
     */
    public CSVUsersReader(String filename){this.file=new File(filename);}
    /**
     * Methods that reads ands parsers the users contained on a certain CSV file
     * @return List with all valid users read from the file
     */
    @Override
    public List<SystemUser> read(){
        List<String> fileContent=FileReader.readFile(file);
        if(fileContent==null){
            return new ArrayList<>();
        }
        List<SystemUser> usersLidos=new ArrayList<>();
        if(fileContent.size()<=1){
            return usersLidos;
        }
        String[] camposFicheiro=fileContent.get(INDENTIFIER_LINE).split(DELIMITER_IDENTIFIER);
        if(camposFicheiro.length!=NUMBER_OF_IDENITIFIERS){
            return new ArrayList<>();
        }
        identifyFields(camposFicheiro);
        for(int i=1;i<fileContent.size();i++){
            String[] nextCampos=fileContent.get(i).split(DELIMITER_IDENTIFIER);
            if(nextCampos.length==NUMBER_OF_IDENITIFIERS){
                try{
                    usersLidos.add(new SystemUser(new Email(nextCampos[emailIdentifier])
                            ,new Name(nextCampos[nameIdentifier],nextCampos[subnameIdentifier])
                            ,null));
                }catch(IllegalArgumentException e){
                    ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME,
                            Level.SEVERE, e.getMessage());
                }
            }
        }
        return usersLidos;
    }
    /**
     * Method that identifies the identifiers(columns) of the CSV file
     * @param fields Array with the identifiers of the columns
     */
    private void identifyFields(String[] fields){
        for(int i=0;i<fields.length;i++){
            if(fields[i].equalsIgnoreCase(EMAIL_COLUMN_IDENTIFIER)){
                this.emailIdentifier=i;
            }
            if(fields[i].equalsIgnoreCase(NAME_COLUMN_IDENTIFIER)){
                this.nameIdentifier=i;
            }
            if(fields[i].equalsIgnoreCase(SUBNAME_COLUMN_IDENTIFIER)){
                this.subnameIdentifier=i;
            }
        }
    }
}
