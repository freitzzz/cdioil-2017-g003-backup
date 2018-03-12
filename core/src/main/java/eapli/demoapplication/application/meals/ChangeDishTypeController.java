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
 * @author Nuno
 */
public class ChangeDishTypeController implements Controller {

    private final ListDishTypeService svc = new ListDishTypeService();

    private final DishTypeRepository repo = PersistenceContext.repositories().dishTypes();

    public DishType changeDishType(DishType theDishType, String newDescription)
            throws DataConcurrencyException, DataIntegrityViolationException {
        Application.ensurePermissionOfLoggedInUser(ActionRight.MANAGE_MENUS);

        if (theDishType == null) {
            throw new IllegalStateException();
        }

        theDishType.changeDescriptionTo(newDescription);

        return this.repo.save(theDishType);
    }

    /**
     * in the context of this use case only active dish types are meaningful.
     *
     * @return
     */
    public Iterable<DishType> listDishTypes() {
        return this.svc.activeDishTypes();
    }
}
