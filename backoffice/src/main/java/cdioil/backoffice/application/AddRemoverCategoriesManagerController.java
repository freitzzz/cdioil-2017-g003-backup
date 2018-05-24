package cdioil.backoffice.application;

import cdioil.domain.Category;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Add/Remove categories from manager user story
 */
public class AddRemoverCategoriesManagerController {

    /**
     * User Repository
     */
    private UserRepositoryImpl userRepository = new UserRepositoryImpl();

    /**
     * Manager Repository
     */
    private ManagerRepositoryImpl managerRepository = new ManagerRepositoryImpl();

    /**
     * Gets a list of all category paths from a manager
     * @param managerEmail managerEmails
     * @return
     */
    public List<String> getManagerCategoriesList(String managerEmail) {
        List<String> categories = new ArrayList<>();

        SystemUser su = userRepository.findByEmail(new Email(managerEmail));

        Manager manager = managerRepository.findBySystemUser(su);

        for (Category cat : manager.categoriesFromManager()) {
            categories.add(cat.categoryPath());
        }

        return categories;
    }
}
