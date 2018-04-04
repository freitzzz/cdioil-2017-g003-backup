package cdioul.frontoffice.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.UserRepositoryImpl;
import cdioil.persistence.BaseJPARepository;

/**
 * Controller of US Change User's Data
 *
 * @author João
 */
public class ChangeUserDataController {

    /**
     * Logged-in user
     */
    private SystemUser su;

    /**
     * Creates an instance of ChangeUserDataController
     *
     * @param su Logged-in user
     */
    public ChangeUserDataController(SystemUser su) {
        this.su = su;
    }

    /**
     * Changes user data
     *
     * @param newData new data
     * @param option int that refers to the data field to be changed
     * @return true if the data was changed successfully, false if not
     */
    public boolean changeDataField(String newData, int option) {
        boolean b = su.changeUserDatafield(newData, option);
        if (b) {
            UserRepositoryImpl repo = new UserRepositoryImpl();
            su=repo.merge(su);
        }
        return b;
    }
}
