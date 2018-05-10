package cdioil.backoffice.application;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.domain.authz.Manager;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * List categories controller
 *
 * @author João
 */
public class ListCategoriesController {

    /**
     * Lists the categories without managers
     *
     * @return list of categories without managers
     */
    public List<Category> listCategoriesWithoutManagers() {
        BaseJPARepository repo = new ManagerRepositoryImpl();
        List<Manager> managers = (List<Manager>) repo.findAll();
        List<MarketStructure> lms = (List<MarketStructure>) new MarketStructureRepositoryImpl().findAll();
        if(lms.isEmpty()){
            return new ArrayList<>();
        }
        MarketStructure marketStruct = lms.get(0);
        List<Category> lc = marketStruct.getAllCategories();
        List<Category> catWithoutManagers = new LinkedList<>();
        for (Category c : lc) {
            boolean b = false;
            for (Manager m : managers) {
                if (m.isAssociatedWithCategory(c)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                catWithoutManagers.add(c);
            }
        }
        return catWithoutManagers;
    }
}
