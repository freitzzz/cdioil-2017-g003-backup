package eapli.demoapplication.persistence;

import eapli.demoapplication.domain.authz.Username;
import eapli.demoapplication.domain.cafeteria.SignupRequest;
import eapli.framework.persistence.repositories.Repository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public interface SignupRequestRepository extends Repository<SignupRequest, Username> {

    Iterable<SignupRequest> pendingSignupRequests();
}
