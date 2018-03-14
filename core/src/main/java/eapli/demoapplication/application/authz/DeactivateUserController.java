/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.authz;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.persistence.UserRepository;
import eapli.demoapplication.Application;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import cdioil.util.DateTime;

/**
 *
 * @author Fernando
 */
public class DeactivateUserController implements Controller {

    private final UserRepository userRepository = PersistenceContext.repositories().users();

    public Iterable<SystemUser> activeUsers() {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        return this.userRepository.findAll();
    }

    public SystemUser deactivateUser(SystemUser user) throws DataConcurrencyException, DataIntegrityViolationException {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        user.deactivate(DateTime.now());
        return this.userRepository.save(user);
    }
}
