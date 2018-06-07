package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

/**
 * The Dashboard view of the Manager Panel
 */
public class DashboardComponent extends DefaultPanelView {

    /**
     * Constructs an instance of the Manager's Dashboard
     */
    public DashboardComponent() {
        super("Dashboard");
        prepareComponents();
    }

    /**
     * Prepares components
     */
    private void prepareComponents() {
        prepareHeader();
        preparePanel();
    }

    /**
     * Creates Window Header
     */
    private void prepareHeader() {
        Responsive.makeResponsive(headerLayout);

        HorizontalLayout toolsLayout = new HorizontalLayout();
        toolsLayout.addStyleName("toolbar");

        Component optionsDropdown = createOptionsDropDown();

        // Create search/filter
        toolsLayout.addComponents(optionsDropdown);

        headerLayout.addComponent(toolsLayout);
        headerLayout.setComponentAlignment(toolsLayout, Alignment.MIDDLE_RIGHT);
        setExpandRatio(headerLayout, 0.10f);
    }

    /**
     * Prepares Panel
     */
    private void preparePanel() {
        Image wipImage = new Image();
        wipImage.setSource(ImageUtils.imagePathAsResource("/WEB-INF/backgrounds/WIP.png"));
        wipImage.setSizeFull();
        addComponent(wipImage);
        setComponentAlignment(wipImage, Alignment.TOP_LEFT);
        setExpandRatio(wipImage, 0.9f);
    }

    /**
     * Create options drop down menu
     * @return
     */
    private Component createOptionsDropDown() {
        MenuBar settingsMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem = settingsMenuBar.addItem("", null);

        menuItem.addItem("Perguntas por categoria",
                VaadinIcons.QUESTION_CIRCLE, (MenuBar.Command) menuItem1
                        -> UI.getCurrent().addWindow(new QuestionsPerCatWindow()));

        return settingsMenuBar;
    }
}
