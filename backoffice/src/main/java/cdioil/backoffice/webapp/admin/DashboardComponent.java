package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.TimeStatisticsController;
import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Dashboard component for the Admin Panel
 */
public class DashboardComponent extends DefaultPanelView {

    private TimeStatisticsController controller;

    private GridLayout layout;

    private DateField startDateField = new DateField();
    private DateField endDateField = new DateField();

    private Label valueLbl1 = new Label();
    private Label valueLbl2 = new Label();
    private Label valueLbl3 = new Label();
    private Label valueLbl4 = new Label();

    /**
     * Constructs an instance of the Dashboard Component for the Admin Panel
     */
    public DashboardComponent() {
        super("Dashboard");

        controller = new TimeStatisticsController(LocalDateTime.MIN, LocalDateTime.MAX);

        setProperties();
        prepareComponents();

        addComponent(layout);

        setExpandRatio(headerLayout, 0.1f);
        setExpandRatio(layout, 0.9f);
    }

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

    private void prepareDatePickers() {
        Responsive.makeResponsive(headerLayout);
        HorizontalLayout topBarLayout = new HorizontalLayout();
        topBarLayout.addStyleName("toolbar");

        startDateField.setPlaceholder("Inicio");
        endDateField.setPlaceholder("Fim");

        startDateField.setValue(LocalDate.MIN);
        endDateField.setValue(LocalDate.now());

        Button updateBtn = new Button("Atualizar");
        updateBtn.addClickListener(onClick -> {
            controller = new TimeStatisticsController(
                    LocalDateTime.of(startDateField.getValue(), LocalTime.MIN),
                    LocalDateTime.of(endDateField.getValue(), LocalTime.MAX));

            refreshStatistics();
        });

        // Create search/filter
        topBarLayout.addComponents(new DateField(), new DateField(), updateBtn);

        headerLayout.addComponent(topBarLayout);
        headerLayout.setComponentAlignment(topBarLayout, Alignment.MIDDLE_RIGHT);
    }

    private void preparePanels() {
        updateLabel(valueLbl1, String.valueOf(controller.getNumberOfSurveysAnswered()));
        layout.addComponent(createDashboardItem("Inquéritos Respondidos",
                "/WEB-INF/dashboard/survey.png", valueLbl1));

        updateLabel(valueLbl2, "456");// TODO placeholder value
        layout.addComponent(createDashboardItem("Logins Aceites",
                "/WEB-INF/dashboard/check.png", valueLbl2));

        updateLabel(valueLbl3, "20");// TODO placeholder value
        layout.addComponent(createDashboardItem("Logins Falhados",
                "/WEB-INF/dashboard/error.png", valueLbl3));

        updateLabel(valueLbl4, "126");// TODO placeholder value
        layout.addComponent(createDashboardItem("Tempo Médio Inquérito",
                "/WEB-INF/dashboard/timer.png", valueLbl4));
    }

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

    private void refreshStatistics() {

    }

    private void updateLabel(Label lbl, String value) {
        lbl.setValue("<h2>" + value + "</h2>");
    }
}
