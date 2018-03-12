package eapli.demoapplication.persistence.inmemory;

import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.persistence.UserRepository;
import eapli.demoapplication.domain.authz.Username;
import eapli.framework.persistence.repositories.impl.inmemory.InMemoryRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryUserRepository extends InMemoryRepository<SystemUser, Username> implements UserRepository {

    @Override
    protected Username newPK(SystemUser u) {
        return u.username();
    }
}
