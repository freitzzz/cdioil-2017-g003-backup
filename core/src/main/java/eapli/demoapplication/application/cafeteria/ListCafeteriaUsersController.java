/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.cafeteria;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.persistence.CafeteriaUserRepository;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.Application;
import eapli.demoapplication.domain.cafeteria.CafeteriaUser;

/**
 *
 * @author losa
 */
public class ListCafeteriaUsersController {

    private final CafeteriaUserRepository repo = PersistenceContext.repositories().cafeteriaUsers();

    public Iterable<CafeteriaUser> activeCafeteriaUsers() {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        return this.repo.findAll();
    }
}
