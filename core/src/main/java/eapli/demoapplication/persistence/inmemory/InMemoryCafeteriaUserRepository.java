package eapli.demoapplication.persistence.inmemory;

import eapli.demoapplication.domain.authz.Username;
import eapli.demoapplication.domain.cafeteria.CafeteriaUser;
import eapli.demoapplication.domain.cafeteria.MecanographicNumber;
import eapli.demoapplication.persistence.CafeteriaUserRepository;
import eapli.framework.persistence.repositories.impl.inmemory.InMemoryRepository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public class InMemoryCafeteriaUserRepository extends InMemoryRepository<CafeteriaUser, MecanographicNumber>
        implements CafeteriaUserRepository {

    @Override
    protected MecanographicNumber newPK(CafeteriaUser u) {
        return u.id();
    }

    @Override
    public CafeteriaUser findByUsername(Username name) {
        return matchOne(e -> e.user().username().equals(name));
    }

    @Override
    public CafeteriaUser findByMecanographicNumber(MecanographicNumber number) {
        return this.repository.get(number);
    }
}
