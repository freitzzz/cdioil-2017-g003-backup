/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application.authz;

import cdioil.domain.authz.*;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.util.List;

/**
 * Controller for the User Story US136 -  Search the users of the system by e-mail.
 * @author Ana Guerra (1161191)
 */
public class SearchUserController {

    /**
     * Instance of the UserRepositoryImpl
     */
    private UserRepositoryImpl userRepos;
    
     /**
     * Sufix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_PREFIX = "^.*";

    /**
     * Prefix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_SUFIX = ".*";
    
    /**
     * Search a SystemUser by email
     * @param email email given
     * @return a list with the system users. Null if the list is empty
     */
    public List<SystemUser> usersByEmail(String email){
        userRepos = new UserRepositoryImpl();
        return userRepos.usersByPattern(email.toLowerCase());
    }
    
}
