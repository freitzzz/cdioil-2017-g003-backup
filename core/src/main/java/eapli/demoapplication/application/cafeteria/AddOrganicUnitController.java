/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.cafeteria;

import eapli.demoapplication.Application;
import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.domain.cafeteria.OrganicUnit;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.persistence.OrganicUnitRepository;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;

/**
 *
 * @author arocha
 */
public class AddOrganicUnitController implements Controller {

    private final OrganicUnitRepository organicUnitRepository = PersistenceContext.repositories().organicUnits();

    public OrganicUnit addOrganicUnit(String acronym, String name, String description)
            throws DataIntegrityViolationException, DataConcurrencyException {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        return this.organicUnitRepository.save(new OrganicUnit(acronym, name, description));
    }
}
