package eapli.demoapplication.application.meals;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.Application;
import eapli.demoapplication.domain.meals.DishType;

/**
 * an application service to avoid code duplication.
 */
class ListDishTypeService {

    private final DishTypeRepository dishTypeRepository = PersistenceContext.repositories().dishTypes();

    public Iterable<DishType> allDishTypes() {
        Application.ensurePermissionOfLoggedInUser(ActionRight.MANAGE_MENUS);

        return this.dishTypeRepository.findAll();
    }

    public Iterable<DishType> activeDishTypes() {
        Application.ensurePermissionOfLoggedInUser(ActionRight.MANAGE_MENUS);

        return this.dishTypeRepository.activeDishTypes();
    }
}
