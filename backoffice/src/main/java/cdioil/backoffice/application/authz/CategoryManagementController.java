package cdioil.backoffice.application.authz;

import cdioil.domain.Category;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

/**
 * Controller class used for US150 - Associar Categorias a um Gestor and US152 - Remover Categorias de um Gestor.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class CategoryManagementController {

    /**
     * Manager chosen by the admin to have categories removed or added.
     */
    private Manager manager;

    /**
     * Path of the category chosen by the admin.
     */
    private String path;

    /**
     * Sufix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_PREFIX = ".*";

    /**
     * Prefix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_SUFIX = ".*";

    /**
     * Scale of the Market Structure values.
     */
    private final static String SCALE = "[0-9]+";

    /**
     * Regular expression to validate the path of the Category in the Market Structure.
     */
    private final static String PATH_REGEX = SCALE + Category.Sufixes.SUFIX_DC + "((-" + SCALE + Category.Sufixes.SUFIX_UN + "(-" + SCALE
            + Category.Sufixes.SUFIX_CAT + "(-" + SCALE + Category.Sufixes.SUFIX_SCAT + "(-" + SCALE + Category.Sufixes.SUFIX_UB + ")?)?)?)?)";

    /**
     * Finds all managers saved in the database.
     *
     * @return iterable of managers
     */
    public Iterable<Manager> listAllManagers() {
        return new ManagerRepositoryImpl().findAll();
    }

    /**
     * Sets the anager chosen by the admin based on the inserted email.
     *
     * @param email email written by the admin on the UI
     * @return true if the manager exists and was set, false if otherwise
     */
    public boolean setManager(String email) {
        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        Email managerEmail = new Email(email);
        SystemUser sysUser = userRepo.findByEmail(managerEmail);
        manager = new ManagerRepositoryImpl().findBySystemUser(sysUser);
        return manager != null;
    }

    /**
     * Sets the path chosen by the admin if it is valid.
     *
     * @param path Path of the categories
     * @return true, if the categories are valid. Otherwise, returns false
     */
    public boolean setPath(String path) {
        if (path.toUpperCase().matches(PATH_REGEX)) {
            this.path = path;
            return true;
        }
        return false;
    }

    /**
     * Removes categories from a manager.
     *
     * @return true if they were removed with success, false if otherwise
     */
    public boolean removeCategories() {
        if (manager.removeCategories(new MarketStructureRepositoryImpl().
                findCategoriesByIdentifierPattern(REGEX_PREFIX + path.toUpperCase() + REGEX_SUFIX)) != false) {
            Manager managerY = new ManagerRepositoryImpl().merge(manager);
            if (managerY != null) {
                manager = managerY;
                return true;
            }
        }
        return false;
    }

    /**
     * Adds categories to a manager.
     *
     * @return true, if the categories are successfully added. Otherwise, returns false
     */
    public boolean addCategories() {
        if (manager.addCategories(new MarketStructureRepositoryImpl().
                findCategoriesByIdentifierPattern(REGEX_PREFIX + path.toUpperCase() + REGEX_SUFIX))) {
            Manager managerY = new ManagerRepositoryImpl().merge(manager);
            if (managerY != null) {
                manager = managerY;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the size of the manager list.
     *
     * @param managerList List with all managers
     * @return the size of the list
     */
    public int size(Iterable<Manager> managerList) {
        int size = 0;
        for (Manager m : managerList) {
            size++;
        }
        return size;
    }
}
