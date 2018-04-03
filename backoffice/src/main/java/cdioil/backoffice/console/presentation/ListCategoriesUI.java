package cdioil.backoffice.console.presentation;

import cdioil.application.ListCategoriesController;
import cdioil.domain.Category;
import java.util.List;

/**
 * List categories UI
 *
 * @author João
 */
public class ListCategoriesUI {

    /**
     * List categories controller
     */
    private ListCategoriesController ctrl;

    /**
     * 1st message the ui displays
     */
    private static final String INTRO_MESSAGE = "Categories without managers:";

    /**
     * Contructor of ListCategoriesUI class
     */
    public ListCategoriesUI() {
        ctrl = new ListCategoriesController();
    }

    /**
     * Lists all categories without managers
     */
    public void listCategoriesWithoutManagers() {
        List<Category> lc = ctrl.listCategoriesWithoutManagers();
        System.out.println(INTRO_MESSAGE);
        for (Category c : lc) {
            System.out.println(c);
        }
    }
}
