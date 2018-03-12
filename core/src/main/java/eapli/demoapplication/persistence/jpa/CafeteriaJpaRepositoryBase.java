package eapli.demoapplication.persistence.jpa;

import eapli.demoapplication.Application;
import eapli.framework.persistence.repositories.impl.jpa.JpaTxRepository;
import java.io.Serializable;

abstract class CafeteriaJpaRepositoryBase<T, K extends Serializable> extends JpaTxRepository<T, K> {

    CafeteriaJpaRepositoryBase(String persistenceUnitName) {
        super(persistenceUnitName);
    }

    CafeteriaJpaRepositoryBase() {
        super(Application.settings().getPersistenceUnitName());
    }
}
