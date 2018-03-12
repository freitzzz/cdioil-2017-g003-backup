package eapli.demoapplication.domain.authz;

import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.persistence.UserRepository;

import java.util.Optional;

/**
 *
 * @author Paulo Gandra Sousa
 *
 */
public class AuthenticationService {

    private final UserRepository repo = PersistenceContext.repositories().users();

    /**
     * Checks if a user can be authenticated with the username/password pair
     *
     * @param username
     * @param pass
     * @return the authenticated user or null otherwise
     */
    public Optional<UserSession> authenticate(Username username, Password pass) {
        if (username == null) {
            throw new IllegalStateException("a username must be provided");
        }
        final Optional<SystemUser> user = retrieveUser(username);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        if (user.get().passwordMatches(pass) && user.get().isActive()) {
            return Optional.of(createSessionForUser(user.get()));
        } else {
            return Optional.empty();
        }
    }

    private UserSession createSessionForUser(SystemUser user) {
        return new UserSession(user);
    }

    private Optional<SystemUser> retrieveUser(Username userName) {
        return this.repo.findOne(userName);
    }
}
