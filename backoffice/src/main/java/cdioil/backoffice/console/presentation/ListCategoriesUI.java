package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ListCategoriesController;
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
     * Message displayed when MarketStructure is null
     */
    private static final String MARKET_STRUCTURE_NULL_MSG = "Market Structure does not exist!";

    /**
     * Constructor of ListCategoriesUI class
     */
    public ListCategoriesUI() {
        ctrl = new ListCategoriesController();
    }

    /**
     * Lists all categories without managers
     */
    public void listCategoriesWithoutManagers() {
        List<Category> lc = ctrl.listCategoriesWithoutManagers();
        if (lc == null || lc.isEmpty()) {
            System.out.println(MARKET_STRUCTURE_NULL_MSG);
        } else {
            System.out.println(INTRO_MESSAGE);
            for (Category c : lc) {
                System.out.println(c);
            }
        }
    }
}
