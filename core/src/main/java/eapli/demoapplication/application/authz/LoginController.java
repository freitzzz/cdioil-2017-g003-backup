package eapli.demoapplication.application.authz;

import eapli.demoapplication.Application;
import eapli.demoapplication.domain.authz.AuthenticationService;
import eapli.demoapplication.domain.authz.Password;
import eapli.demoapplication.domain.authz.UnableToAuthenticateException;
import eapli.demoapplication.domain.authz.UserSession;
import eapli.demoapplication.domain.authz.Username;
import eapli.framework.application.Controller;
import java.util.Optional;

/**
 * Created by nuno on 21/03/16.
 */
public class LoginController implements Controller {

    private final AuthenticationService authenticationService = new AuthenticationService();

    /**
     * This method allows a user to perform login and creates the session.
     *
     * @param userName
     * @param password
     */
    public void login(String userName, String password) throws UnableToAuthenticateException {
        final Optional<UserSession> newSession = this.authenticationService.authenticate(new Username(userName),
                new Password(password));
        if (newSession.isPresent()) {
            Application.session().setSession(newSession.get());
        } else {
            throw new UnableToAuthenticateException("Invalid user/pass");
        }
    }
}
