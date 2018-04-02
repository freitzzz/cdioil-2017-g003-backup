package cdioil.backoffice.console.presentation;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Main class for the application's backoffice.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BackOfficeMain {
    public static void main(String[] args){
        
        //Load localize strings
        try {
            BackOfficeLocalizationHandler.getInstance().loadStrings();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(BackOfficeMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        new BackOfficeLogin().backofficeLogin();}
    
}
