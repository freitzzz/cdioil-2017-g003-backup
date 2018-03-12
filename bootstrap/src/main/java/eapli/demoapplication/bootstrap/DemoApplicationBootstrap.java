package eapli.demoapplication.bootstrap;

import eapli.demoapplication.Application;
import eapli.demoapplication.bootstrap.domain.authz.UsersBootstrap;
import eapli.demoapplication.bootstrap.domain.cafeteria.OrganicUnitBootsrap;
import eapli.demoapplication.bootstrap.domain.meals.DishTypesBootstrap;
import eapli.demoapplication.domain.authz.RoleType;
import eapli.demoapplication.domain.authz.SystemUser;
import eapli.demoapplication.domain.authz.UserSession;
import eapli.framework.actions.Action;
import java.util.HashSet;
import java.util.Set;

/**
 * Demo Application Bootstrapping data app.
 *
 */
public class DemoApplicationBootstrap implements Action {

    public static void main(String[] args) {
        System.out.println("Bootstrapping Demo Application 2016(c) data");

        new DemoApplicationBootstrap().execute();
    }

    @Override
    public boolean execute() {
        // declare bootstrap actions
        final Action[] actions = {new UsersBootstrap(), new DishTypesBootstrap(), new OrganicUnitBootsrap()};

        // authenticate a super user to be able to register new users, ...
        // in this case we will inject the session but we shouldn't do this
        // AuthenticationService authz = new AuthenticationService();
        // Session adminSession = authz.authenticate(new Username("admin"), new
        // Password("admin"));
        final Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ADMIN);
        roles.add(RoleType.MENU_MANAGER);
        final UserSession adminSession = new UserSession(
                new SystemUser("poweruser", "poweruser", "joe", "doe", "joe@email.org", roles));
        Application.session().setSession(adminSession);

        // execute all bootstrapping returning true if any of the bootstraping
        // actions returns true
        boolean ret = false;
        for (final Action boot : actions) {
            ret |= boot.execute();
        }
        return ret;
    }
}
