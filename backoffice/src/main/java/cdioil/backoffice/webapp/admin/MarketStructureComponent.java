package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.MarketStructureComponentController;
import cdioil.backoffice.webapp.DefaultPanelView;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;

/**
 * Market Structure view
 */
public class MarketStructureComponent extends DefaultPanelView {

    /**
     * Main Panel
     */
    private Panel mainPanel;

    /**
     * Controller Class
     */
    private MarketStructureComponentController controller;

    /**
     * Constructs an instance of the Market Structure view
     */
    public MarketStructureComponent() {
        super("Estrutura Mercadológica");
        prepareComponents();
    }

    /**
     * Prepares components
     */
    private void prepareComponents() {
        Tree<String> sampleTree = new Tree<>();

        mainPanel = new Panel(sampleTree);
        mainPanel.setSizeFull();

        controller = new MarketStructureComponentController();

        TreeData<String> data = new TreeData<>();
        data.addItems(null, controller.getRootItems());

        sampleTree.setDataProvider(new TreeDataProvider<>(data));

        addComponent(mainPanel);

        setExpandRatio(headerLayout, 0.10f);
        setExpandRatio(mainPanel, 0.90f);
    }
}
