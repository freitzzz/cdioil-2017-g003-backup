package eapli.demoapplication.persistence.jpa;

import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.demoapplication.domain.meals.DishType;

/**
 * Created by MCN on 29/03/2016.
 */
class JpaDishTypeRepository extends CafeteriaJpaRepositoryBase<DishType, Long> implements DishTypeRepository {

    @Override
    public Iterable<DishType> activeDishTypes() {
        return match("e.active=true");
    }

    @Override
    public DishType findByAcronym(String acronym) {
        return matchOne("e.acronym=:acronym", "acronym", acronym);
    }
}
