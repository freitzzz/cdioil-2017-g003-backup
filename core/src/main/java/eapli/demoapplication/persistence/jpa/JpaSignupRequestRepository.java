package eapli.demoapplication.persistence.jpa;

import eapli.demoapplication.domain.authz.Username;
import eapli.demoapplication.domain.cafeteria.SignupRequest;
import eapli.demoapplication.persistence.SignupRequestRepository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
class JpaSignupRequestRepository extends CafeteriaJpaRepositoryBase<SignupRequest, Username>
        implements SignupRequestRepository {

    @Override
    public Iterable<SignupRequest> pendingSignupRequests() {
        return match("e.approvalStatus=ApprovalStatus.PENDING");
    }
}
