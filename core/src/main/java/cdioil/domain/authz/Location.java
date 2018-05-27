package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * Location class that represents a certain location
 * <br>Value Object of SystemUser
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Location implements ValueObject,Serializable{
    /**
     * Constant that represents an empty String
     */
    private static final String EMPTY_STRING="";
    /**
     * Constant that represents the regular expression used to identify all 
     * possible spaces
     */
    private static final String REGEX_ALL_SPACES="\\s+";
    /**
     * Constant that represents the message of an invalid location
     */
    private static final String INVALID_LOCATION_MESSAGE="A localidade é inválida!";
    /**
     * String that represents the location of a certain SystemUser
     */
    private String locale;
    /**
     * Builds a new Location that represents the location of a SystemUser
     * @param location String with the SystemUser location
     */
    public Location(String location){
        checkLocation(location);
        this.locale=location;
    }
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected Location(){}
    /**
     * Method that checks if a certain Location is equal to the current Location
     * @param obj Location with the location being compared with the current one
     * @return boolean true if both locations are equal, false if not
     */
    @Override
    public boolean equals(Object obj){
        if(obj==this){
            return true;
        }
        if(obj==null||obj.getClass()!=this.getClass()){
            return false;
        }
        return locale.equalsIgnoreCase(((Location)obj).locale);
    }
    /**
     * Location Hashcode
     * @return Integer with the current Location hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.locale);
        return hash;
    }
    /**
     * Method that represents the textual information of a Location
     * @return String with the textual representation of the current Location
     */
    @Override
    public String toString(){
        return locale;
    }
    /**
     * Method that checks if a certain location is valid or not
     * <br>Throws an <b>IllegalArgumentException</b> if the location is invalid
     * <br><b>Warning</b>: Currently not regexing all possible Locations, due to 
     * application localization
     * @param location String with the location being validated
     */
    private void checkLocation(String location){
        if(location==null||location.replaceAll(REGEX_ALL_SPACES,EMPTY_STRING).isEmpty()){
            throw new IllegalArgumentException(INVALID_LOCATION_MESSAGE
                    ,new Throwable(this.getClass().getSimpleName()));
        }
    }
}
