package eapli.demoapplication.persistence;

import eapli.demoapplication.domain.meals.DishType;
import eapli.framework.persistence.repositories.Repository;

/**
 * the repository for Dish Types.
 *
 * It uses spring Data to automatically generate the implementation class.
 */
public interface DishTypeRepository extends Repository<DishType, Long> {

    /**
     * returns the active dish types
     *
     * @return
     */
    Iterable<DishType> activeDishTypes();

    DishType findByAcronym(String acronym);
}
