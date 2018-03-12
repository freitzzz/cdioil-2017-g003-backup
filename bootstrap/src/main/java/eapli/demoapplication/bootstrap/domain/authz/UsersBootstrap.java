/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.bootstrap.domain.authz;

import eapli.demoapplication.application.authz.AddUserController;
import eapli.demoapplication.domain.authz.RoleType;
import eapli.framework.actions.Action;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Paulo Gandra Sousa
 */
public class UsersBootstrap implements Action {

    @Override
    public boolean execute() {
        registerAdmin();
        registerCashier();
        registerUser();
        registerKitchenManager();
        registerMenuManager();
        return false;
    }

    /**
     *
     */
    private void registerAdmin() {
        final String username = "admin";
        final String password = "admin";

        final String firstName = "Mary";
        final String lastName = "Admin";

        final String email = "mary.doe@emai.l.com";

        final Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ADMIN);

        final AddUserController userController = new AddUserController();
        try {
            userController.addUser(username, password, firstName, lastName, email, roles);
        } catch (final Exception e) {
            // ignoring exception. assuming it is justa primiray key violation
            // due to the tentative of inserting a duplicated user
        }
    }

    private void registerCashier() {
        final String username = "cashier";
        final String password = "cashier";

        final String firstName = "Johny";
        final String lastName = "Cash";

        final String email = "johny.doe@emai.l.com";

        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.CASHIER);

        final AddUserController userController = new AddUserController();
        try {
            userController.addUser(username, password, firstName, lastName, email, roles);
        } catch (final Exception e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
        }
    }

    private void registerUser() {
        final String username = "user";
        final String password = "user";

        final String firstName = "The";
        final String lastName = "User";

        final String email = "the.user@emai.l.com";

        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.USER);

        final AddUserController userController = new AddUserController();
        try {
            userController.addUser(username, password, firstName, lastName, email, roles);
        } catch (final Exception e) {
            // ignoring exception. assuming it is justa primiray key violation
            // due to the tentative of inserting a duplicated user
        }
    }

    private void registerKitchenManager() {
        final String username = "kitchen";
        final String password = "kitchen";

        final String firstName = "Oven";
        final String lastName = "Stove";

        final String email = "Oven.and.stove@emai.l.com";

        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.KITCHEN_MANAGER);

        final AddUserController userController = new AddUserController();
        try {
            userController.addUser(username, password, firstName, lastName, email, roles);
        } catch (final Exception e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
        }
    }

    private void registerMenuManager() {
        final String username = "chef";
        final String password = "chef";

        final String firstName = "Master";
        final String lastName = "Chef";

        final String email = "master.chef@emai.l.com";

        final Set<RoleType> roles = new HashSet<RoleType>();
        roles.add(RoleType.MENU_MANAGER);

        final AddUserController userController = new AddUserController();
        try {
            userController.addUser(username, password, firstName, lastName, email, roles);
        } catch (final Exception e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
        }
    }
}
