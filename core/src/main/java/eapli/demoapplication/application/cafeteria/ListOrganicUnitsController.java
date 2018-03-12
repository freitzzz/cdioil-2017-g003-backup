/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.cafeteria;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.domain.cafeteria.OrganicUnit;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.Application;
import eapli.demoapplication.persistence.OrganicUnitRepository;
import eapli.framework.application.Controller;

/**
 *
 * @author losa
 */
public class ListOrganicUnitsController implements Controller {

    private final OrganicUnitRepository repo = PersistenceContext.repositories().organicUnits();

    public Iterable<OrganicUnit> listOrganicUnits() {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        return this.repo.findAll();
    }
}
