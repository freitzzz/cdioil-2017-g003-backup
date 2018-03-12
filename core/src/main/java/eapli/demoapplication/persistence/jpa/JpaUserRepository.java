package eapli.demoapplication.persistence.jpa;

import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.domain.authz.Username;
import eapli.demoapplication.persistence.UserRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
class JpaUserRepository extends CafeteriaJpaRepositoryBase<SystemUser, Username> implements UserRepository {

}
