package eapli.demoapplication.application;

import eapli.demoapplication.Application;
import eapli.demoapplication.application.authz.AddUserController;
import eapli.demoapplication.domain.authz.RoleType;
import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.domain.authz.UnableToAuthenticateException;
import eapli.demoapplication.domain.authz.UserSession;
import eapli.util.DateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Nuno Bettencourt [NMB] on 24/03/16.
 */
public class AddUserControllerTest {

    @BeforeClass
    public static void setUp() throws UnableToAuthenticateException {
        // in this case we will inject the session but we shouldn't do this
        // AuthenticationService authz = new AuthenticationService();
        // Session adminSession = authz.authenticate(new Username("admin"), new
        // Password("admin"));
        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.ADMIN);
        final UserSession adminSession = new UserSession(
                new SystemUser("admin", "admin", "joe", "doe", "joe@email.org", roles));
        Application.session().setSession(adminSession);
    }

    @Test
    public void attestNormalBehaviour() throws Exception {

        final String userName = "john";
        final String password = "johndoe";
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "johndoe@email.com";
        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.ADMIN);
        roles.add(RoleType.CASHIER);

        final Calendar createdOn = DateTime.now();
        final SystemUser expected = new SystemUser(userName, password, firstName, lastName, email, roles, createdOn);

        final AddUserController controller = new AddUserController();

        final SystemUser result = controller.addUser(userName, password, firstName, lastName, email, roles, createdOn);
        assertTrue("the added user does not have the same data as input", expected.sameAs(result));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureRoleTypeListIsNotNull() throws Exception {

        final String userName = "john";
        final String password = "johndoe";
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "johndoe@email.com";
        final Set<RoleType> roles = null;

        final SystemUser expected = new SystemUser(userName, password, firstName, lastName, email, roles);

        final AddUserController controller = new AddUserController();

        final SystemUser result = controller.addUser(userName, password, firstName, lastName, email, roles);
        assertEquals(expected, result);
    }
    /*
     * @Test(expected=IllegalArgumentException.class) public void
     * ensureRoleTypeListIsNotEmpty() throws Exception {
     *
     * String userName = "john"; String password = "johndoe"; String firstName =
     * "John"; String lastName = "Doe"; String email = "johndoe@email.com";
     * List<RoleType> roles = new ArrayList<>();
     *
     * User expected = new User(userName, password, firstName, lastName, email,
     * roles);
     *
     * AddUserController controller = new AddUserController();
     *
     * User result = controller.addUser(userName, password, firstName, lastName,
     * email, roles); assertEquals(expected, result); }
     */
}
