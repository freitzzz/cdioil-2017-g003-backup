package cdioil.backoffice.application.authz;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;

public class AssignManagerController {

    /**
     * UserRepository Implementation
     */
    private final UserRepositoryImpl systemUserRepository;

    /**
     * Manager Repository Implementation
     */
    private final ManagerRepositoryImpl managerRepository;

    /**
     * Admin Repository Implementatation
     */
    private final AdminRepositoryImpl adminRepository;

    /**
     * Constructor
     */
    public AssignManagerController() {
        this.systemUserRepository = new UserRepositoryImpl();
        this.managerRepository = new ManagerRepositoryImpl();
        this.adminRepository = new AdminRepositoryImpl();
    }

    /**
     * Method that verifies if a SystemUser is a manager.
     *
     * @param sysUserEmail
     * @return true, if the SystemUser is a manager. Otherwise, returns false
     */
    public boolean isManager(String sysUserEmail) {
        SystemUser su = systemUserRepository.findByEmail(new Email(sysUserEmail));

        // Lista de managers
        List<Manager> managers
                = (List<Manager>) managerRepository.findAll();
        return managers.stream().anyMatch(manager -> (manager.getID().equals(su)));
    }

    /**
     * Method that verifies if a SystemUser is an admin.
     *
     * @param sysUserEmail email
     * @return true, if the SystemUser is an admin. Otherwise, returns false
     */
    public boolean isAdmin(String sysUserEmail) {
        SystemUser su = systemUserRepository.findByEmail(new Email(sysUserEmail));
        // Lista de admins
        List<Admin> admins
                = (List<Admin>) adminRepository.findAll();
        return admins.stream().anyMatch(admin -> (admin.getID().equals(su)));
    }

    /**
     * From a given email, find a user and assigns it the role of manager
     *
     * @param email given email
     */
    public void assignManager(String email, List<String> categoryPaths) {
        if (email == null || categoryPaths == null) {
            throw new IllegalArgumentException("Email or list of categories cannot be null");
        }

        if (categoryPaths.isEmpty()) {
            throw new IllegalArgumentException("Category list cannot be empty");
        }

        // Buscar SystemUser com email dado
        Email e = new Email(email);
        SystemUser su = systemUserRepository.findByEmail(e);

        if (su == null) {
            throw new IllegalArgumentException("User with given email does not exist");
        }

        MarketStructureRepositoryImpl marketStructureRepository =
                new MarketStructureRepositoryImpl();

        List<Category> categoriesToBeAdded = new ArrayList<>();

        for (String currentCatSring : categoryPaths) {
            // Check if Category is valid and exists
            List<Category> foundCategories = marketStructureRepository
                    .findCategoriesByPathPattern(currentCatSring);

            categoriesToBeAdded.addAll(foundCategories);
        }

        if (categoriesToBeAdded.isEmpty()) {
            throw new IllegalArgumentException("Não foi encontrada nenhuma categoria");
        }

        // Adicionar Manager
        Manager g = new Manager(su);

        if (g.addCategories(categoriesToBeAdded)) {
            //TODO error adding categories
        }

        // Persiste
        managerRepository.add(g);
    }
}
