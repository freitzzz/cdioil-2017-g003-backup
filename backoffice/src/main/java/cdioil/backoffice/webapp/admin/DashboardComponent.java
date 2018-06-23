package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.TimeStatisticsController;
import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDateTime;

/**
 * Dashboard component for the Admin Panel
 */
public class DashboardComponent extends DefaultPanelView {

    /**
     * Controller Class
     */
    private transient TimeStatisticsController controller;

    /**
     * Main Layout
     */
    private GridLayout layout;

    private DateTimeField startDateField = new DateTimeField();
    private DateTimeField endDateField = new DateTimeField();

    private Label valueLbl1 = new Label();
    private Label valueLbl2 = new Label();
    private Label valueLbl3 = new Label();
    private Label valueLbl4 = new Label();

    /**
     * Constructs an instance of the Dashboard Component for the Admin Panel
     */
    public DashboardComponent() {
        super("Dashboard");

        controller = new TimeStatisticsController(LocalDateTime.now().minusDays(7),
                LocalDateTime.now());

        setProperties();
        prepareComponents();

        addComponent(layout);

        setExpandRatio(headerLayout, 0.1f);
        setExpandRatio(layout, 0.9f);
    }

    /**
     * Sets window properties
     */
    private void setProperties() {
        layout = new GridLayout(2, 2);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
    }

    /**
     * Prepares Components
     */
    private void prepareComponents() {
        prepareDatePickers();
        preparePanels();
    }

    /**
     * Prepares date pickers
     */
    private void prepareDatePickers() {
        Responsive.makeResponsive(headerLayout);
        HorizontalLayout topBarLayout = new HorizontalLayout();
        topBarLayout.addStyleName("toolbar");

        startDateField.setPlaceholder("Inicio");
        endDateField.setPlaceholder("Fim");

        Button updateBtn = new Button("Atualizar");
        updateBtn.addClickListener(onClick -> {
            try {
                controller = new TimeStatisticsController(startDateField.getValue(),
                        endDateField.getValue());
            } catch (IllegalArgumentException e) {
                Notification.show("Intervalo de tempo inválido",
                        Notification.Type.ERROR_MESSAGE);
                return;
            }

            refreshStatistics();
        });

        // Create search/filter
        topBarLayout.addComponents(startDateField, endDateField, updateBtn);

        headerLayout.addComponent(topBarLayout);
        headerLayout.setComponentAlignment(topBarLayout, Alignment.MIDDLE_RIGHT);
    }

    /**
     * Prepare all panels
     */
    private void preparePanels() {
        layout.addComponent(createDashboardItem("Inquéritos Respondidos",
                "/WEB-INF/dashboard/survey.png", valueLbl1));

        layout.addComponent(createDashboardItem("Logins Aceites",
                "/WEB-INF/dashboard/check.png", valueLbl2));

        layout.addComponent(createDashboardItem("Logins Falhados",
                "/WEB-INF/dashboard/error.png", valueLbl3));

        layout.addComponent(createDashboardItem("Tempo Médio Inquérito",
                "/WEB-INF/dashboard/timer.png", valueLbl4));

        refreshStatistics();
    }

    /**
     * Creates a new DashBoard item
     *
     * @param title   item
     * @param imgPath image path
     * @param label   label
     * @return Panel
     */
    private Panel createDashboardItem(String title, String imgPath, Label label) {
        VerticalLayout panelLayout = new VerticalLayout();
        panelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // Header
        Label headerLabel = new Label("<h2>" + title + "</h2>");
        headerLabel.setContentMode(ContentMode.HTML);

        // Image
        Image img = new Image();
        img.setSource(ImageUtils.imagePathAsResource(imgPath));
        img.setWidth(30, Unit.PERCENTAGE);
        img.setHeight(30, Unit.PERCENTAGE);

        HorizontalLayout titleImageLayout = new HorizontalLayout(headerLabel, img);
        titleImageLayout.setComponentAlignment(headerLabel, Alignment.MIDDLE_RIGHT);
        titleImageLayout.setComponentAlignment(img, Alignment.TOP_RIGHT);
        titleImageLayout.setSpacing(true);
        titleImageLayout.setWidth(100, Unit.PERCENTAGE);
        titleImageLayout.setHeight(60, Unit.PERCENTAGE);

        label.setContentMode(ContentMode.HTML);

        panelLayout.addComponents(titleImageLayout, label);

        Panel panel = new Panel(panelLayout);
        panel.setSizeFull();

        return panel;
    }

    /**
     * Refreshes statistics to accommodate new Time Period
     */
    private void refreshStatistics() {
        updateLabel(valueLbl1, String.valueOf(controller.getNumberOfSurveysAnswered()));
        updateLabel(valueLbl2, String.valueOf(controller.getNumberOfValidLogins()));
        updateLabel(valueLbl3, String.valueOf(controller.getNumberOfInvalidLogins()));
        updateLabel(valueLbl4, String.valueOf(controller.getReviewsAnswerAverageTime()));
    }

    /**
     * Updates a given label
     *
     * @param lbl   label
     * @param value new value
     */
    private void updateLabel(Label lbl, String value) {
        lbl.setValue("<h2>" + value + "</h2>");
    }

    private void treatDateValues(LocalDateTime x, LocalDateTime y) {
    }
}
