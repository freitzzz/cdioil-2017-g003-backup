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

/**
 *
 * @author losa
 */
public class ListUsersController implements Controller {

    private final UserRepository userRepository = PersistenceContext.repositories().users();

    public Iterable<SystemUser> allUsers() {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        return this.userRepository.findAll();
    }
}
