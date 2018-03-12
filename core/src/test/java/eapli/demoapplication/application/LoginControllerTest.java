package eapli.demoapplication.application;

import eapli.demoapplication.Application;
import eapli.demoapplication.application.authz.AddUserController;
import eapli.demoapplication.application.authz.LoginController;
import eapli.demoapplication.domain.authz.RoleType;
import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.domain.authz.UnableToAuthenticateException;
import java.util.HashSet;
import java.util.Set;

import eapli.demoapplication.domain.authz.UserSession;
import org.junit.After;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nuno Bettencourt [NMB] on 24/03/16.
 */
public class LoginControllerTest {
	@Before
	public void setUp() throws Exception {
		final Set<RoleType> roles = new HashSet<RoleType>();
		roles.add(RoleType.ADMIN);
		final UserSession adminSession = new UserSession(
				new SystemUser("admin", "admin", "joe", "doe", "joe@email.org", roles));
		Application.session().setSession(adminSession);
	}

	@After
    public void tearDown() throws Exception {
        System.getProperties().remove("persistence.repositoryFactory");
    }

    @Test
    public void ensureUserIsAllowed() throws Exception {
        System.getProperties().setProperty("persistence.repositoryFactory",
                "InMemoryRepositoryFactory");

        final String userName = "admin";
        final String password = "admin";
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "john.doe@emai.l.com";

        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.ADMIN);

        final AddUserController userController = new AddUserController();
        userController.addUser(userName, password, firstName, lastName, email, roles);

        final LoginController controller = new LoginController();
        controller.login(userName, password);
        assertTrue(true);
    }

    @Test(expected = UnableToAuthenticateException.class)
    public void ensureInvalidUser() throws Exception {
        System.getProperties().setProperty("persistence.repositoryFactory",
                "InMemoryRepositoryFactory");

        final String userName = "admin";
        final String password = "admin";
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "john.doe@emai.l.com";

        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.ADMIN);

        final AddUserController userController = new AddUserController();
        userController.addUser(userName, password, firstName, lastName, email, roles);

        final String inputUserName = "admin1";
        final String inputPassword = "admin1";

        final LoginController controller = new LoginController();
        controller.login(inputUserName, inputPassword);
        assertTrue(true);
    }

    @Test(expected = UnableToAuthenticateException.class)
    public void ensureInvalidPassword() throws Exception {
        System.getProperties().setProperty("persistence.repositoryFactory",
                "InMemoryRepositoryFactory");

        final String userName = "admin";
        final String password = "admin";
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "john.doe@emai.l.com";

        final Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ADMIN);

        final AddUserController userController = new AddUserController();
        userController.addUser(userName, password, firstName, lastName, email, roles);

        final String inputUserName = "admin";
        final String inputPassword = "admin1";

        final LoginController controller = new LoginController();
        controller.login(inputUserName, inputPassword);
        assertTrue(true);
    }
    /*
     * @Test(expected = InvalidUserException.class) public void
     * ensureInvalidAccessWithEmptyMemoryDatabase() throws Exception {
     * System.getProperties().remove("persistence.repositoryFactory");
     * System.getProperties().setProperty("persistence.repositoryFactory",
     * "InMemoryRepositoryFactory");
     *
     * final String inputUserName = "admin"; final String inputPassword =
     * "admin";
     *
     * final LoginController controller = new LoginController();
     * controller.login(inputUserName, inputPassword); assertTrue(true); }
     */
}
