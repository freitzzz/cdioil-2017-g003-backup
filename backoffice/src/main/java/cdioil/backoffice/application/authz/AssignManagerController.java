package cdioil.backoffice.application.authz;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class AssignManagerController {

    /**
     * UserRepository Implementation
     */
    private UserRepositoryImpl systemUserRepository;

    /**
     * Manager Repository Implementation
     */
    private ManagerRepositoryImpl managerRepository;

    /**
     * Admin Repository Implementatation
     */
    private AdminRepositoryImpl adminRepository;

    /**
     * Constructor
     */
    public AssignManagerController() {
        this.systemUserRepository = new UserRepositoryImpl();
        this.managerRepository = new ManagerRepositoryImpl();
        this.adminRepository = new AdminRepositoryImpl();
    }

    /**
     * Fetches a list of registered users
     * @return arraylist with emails from registered users
     */
    public ArrayList<String> registeredUsers() {

        // Lista de System user
        List<SystemUser> systemUsers =
                (List<SystemUser>) systemUserRepository.findAll();

        // Lista de managers
        List<Manager> managers =
                (List<Manager>) managerRepository.findAll();

        // Lista de admins
        List<Admin> admins =
                (List<Admin>) adminRepository.findAll();

        /*
        Contem todos os utilizadores que nao sao managers nem admins
         */
        ArrayList<String> validUsers = new ArrayList<>();

        for (SystemUser su : systemUsers) {
            if (!managers.contains(su) && !admins.contains(su)) {
                validUsers.add(su.toString());
            }
        }

        return validUsers;
    }


    /**
     * From a given email, find a user and assigns it the role of manager
     * @param email given email
     */
    public void assignManager(String email) {
        // Buscar SystemUser com email dado
        Email e = new Email(email);
        SystemUser su = systemUserRepository.findByEmail(e);

        // Adicionar Manager
        Manager g = new Manager(su);

        // Persiste
        managerRepository.add(g);
    }
}