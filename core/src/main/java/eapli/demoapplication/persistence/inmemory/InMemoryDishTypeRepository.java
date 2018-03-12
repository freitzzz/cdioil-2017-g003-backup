package eapli.demoapplication.persistence.inmemory;

import eapli.demoapplication.domain.meals.DishType;
import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.framework.persistence.repositories.impl.inmemory.InMemoryRepositoryWithLongPK;

/**
 * Created by MCN on 29/03/2016.
 */
public class InMemoryDishTypeRepository extends InMemoryRepositoryWithLongPK<DishType> implements DishTypeRepository {

    @Override
    public Iterable<DishType> activeDishTypes() {
        return match(e -> e.isActive());
    }

    @Override
    public DishType findByAcronym(String acronym) {
        return matchOne(e -> e.id().equals(acronym));
    }
}
