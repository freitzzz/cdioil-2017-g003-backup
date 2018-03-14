package eapli.demoapplication.application.cafeteria;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.domain.cafeteria.OrganicUnit;
import eapli.demoapplication.domain.cafeteria.SignupRequest;
import eapli.demoapplication.domain.cafeteria.SignupRequestBuilder;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.persistence.SignupRequestRepository;
import eapli.demoapplication.Application;
import eapli.demoapplication.persistence.OrganicUnitRepository;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import cdioil.util.DateTime;
import java.util.Calendar;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
public class SignupController implements Controller {

    private final SignupRequestRepository signupRequestRepository = PersistenceContext.repositories().signupRequests();
    private final OrganicUnitRepository organicUnitRepository = PersistenceContext.repositories().organicUnits();

    public SignupRequest signup(final String username, final String password, final String firstName,
                                final String lastName, final String email, OrganicUnit organicUnit, String mecanographicNumber,
                                final Calendar createdOn) throws DataIntegrityViolationException, DataConcurrencyException {

        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        final SignupRequestBuilder signupRequestBuilder = new SignupRequestBuilder();
        signupRequestBuilder.withUsername(username).withPassword(password).withFirstName(firstName)
                .withLastName(lastName).withEmail(email).withCreatedOn(createdOn).withOrganicUnit(organicUnit)
                .withMecanographicNumber(mecanographicNumber);

        final SignupRequest newSignupRequest = signupRequestBuilder.build();
        return this.signupRequestRepository.save(newSignupRequest);
    }

    public SignupRequest signup(final String username, final String password, final String firstName,
            final String lastName, final String email, OrganicUnit organicUnit, String mecanographicNumber)
            throws DataIntegrityViolationException, DataConcurrencyException {

        return SignupController.this.signup(username, password, firstName, lastName, email, organicUnit,
                mecanographicNumber, DateTime.now());
    }

    public Iterable<OrganicUnit> organicUnits() {
        return this.organicUnitRepository.findAll();
    }
}
