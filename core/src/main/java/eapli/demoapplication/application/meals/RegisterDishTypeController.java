/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.meals;

import eapli.demoapplication.Application;
import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.demoapplication.domain.meals.DishType;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;

/**
 *
 * @author mcn
 */
public class RegisterDishTypeController implements Controller {

    private final DishTypeRepository repository = PersistenceContext.repositories().dishTypes();

    public DishType registerDishType(String acronym, String description)
            throws DataIntegrityViolationException, DataConcurrencyException {
        Application.ensurePermissionOfLoggedInUser(ActionRight.MANAGE_MENUS);

        final DishType newDishType = new DishType(acronym, description);
        return this.repository.save(newDishType);
    }
}
