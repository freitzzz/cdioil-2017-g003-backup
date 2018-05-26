package cdioil.backoffice.application;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the market strucure component
 */
public class MarketStructureComponentController {

    /**
     * Market Structure Repository
     */
    private MarketStructureRepositoryImpl repository;

    /**
     * Creates an instance of the Controller class
     */
    public MarketStructureComponentController() {
        repository = new MarketStructureRepositoryImpl();
    }

    /**
     * Gets market structure root items
     * @return
     */
    public List<String> getRootItems() {
        MarketStructure mk = repository.findMarketStructure();

        List<Category> rootItems =
                mk.getDirectChildren(new Category("Todos os Produtos", "RAIZ"));

        List<String> rootItemNames = new ArrayList<>();
        for (Category cat :
                rootItems) {
            rootItemNames.add(cat.categoryName());
        }

        return rootItemNames;
    }
}
