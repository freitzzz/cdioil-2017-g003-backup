/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.meals;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.Application;
import eapli.demoapplication.domain.meals.DishType;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;

/**
 *
 * @author mcn
 */
public class ActivateDeactivateDishTypeController implements Controller {

    private final ListDishTypeService svc = new ListDishTypeService();
    private final DishTypeRepository dishTypeRepository = PersistenceContext.repositories().dishTypes();

    public Iterable<DishType> allDishTypes() {
        return this.svc.allDishTypes();
    }

    public void changeDishTypeState(DishType dType) throws DataConcurrencyException, DataIntegrityViolationException {
        Application.ensurePermissionOfLoggedInUser(ActionRight.MANAGE_MENUS);

        dType.toogleState();
        this.dishTypeRepository.save(dType);
    }
}
