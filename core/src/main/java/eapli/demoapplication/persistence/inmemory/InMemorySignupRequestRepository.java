package eapli.demoapplication.persistence.inmemory;

import eapli.demoapplication.persistence.SignupRequestRepository;
import eapli.demoapplication.domain.authz.Username;
import eapli.demoapplication.domain.cafeteria.SignupRequest;
import eapli.framework.persistence.repositories.impl.inmemory.InMemoryRepository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public class InMemorySignupRequestRepository extends InMemoryRepository<SignupRequest, Username>
        implements SignupRequestRepository {

    @Override
    protected Username newPK(SignupRequest u) {
        return u.id();
    }

    @Override
    public Iterable<SignupRequest> pendingSignupRequests() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
