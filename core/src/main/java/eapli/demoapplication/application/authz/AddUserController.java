package eapli.demoapplication.application.authz;

import eapli.demoapplication.domain.authz.ActionRight;
import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.persistence.UserRepository;
import eapli.demoapplication.Application;
import eapli.demoapplication.domain.authz.RoleType;
import eapli.demoapplication.domain.authz.UserBuilder;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import cdioil.util.DateTime;
import java.util.Calendar;
import java.util.Set;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class AddUserController implements Controller {

    private final UserRepository userRepository = PersistenceContext.repositories().users();

    public SystemUser addUser(String username, String password, String firstName, String lastName, String email,
                              Set<RoleType> roles, Calendar createdOn) throws DataIntegrityViolationException, DataConcurrencyException {
        Application.ensurePermissionOfLoggedInUser(ActionRight.ADMINISTER);

        final UserBuilder userBuilder = new UserBuilder();
        userBuilder.withUsername(username).withPassword(password).withFirstName(firstName).withLastName(lastName)
                .withEmail(email).withCreatedOn(createdOn).withRoles(roles);

        return this.userRepository.save(userBuilder.build());
    }

    public SystemUser addUser(String username, String password, String firstName, String lastName, String email,
            Set<RoleType> roles) throws DataIntegrityViolationException, DataConcurrencyException {
        return addUser(username, password, firstName, lastName, email, roles, DateTime.now());
    }
}
