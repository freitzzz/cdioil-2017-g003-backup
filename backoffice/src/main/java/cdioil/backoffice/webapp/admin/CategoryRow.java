package cdioil.backoffice.webapp.admin;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/**
 * Represents a Category Row (Label + TextField)
 */
public class CategoryRow extends HorizontalLayout {

    /**
     * Category Label
     */
    private Label categoryLbl;

    /**
     * Row Index
     */
    private int idx;

    /**
     * Category TextField
     */
    private TextField categoryInput;

    /**
     * Creates a new Category Row with a given index
     * @param idx index
     */
    protected CategoryRow(int idx) {
        categoryLbl = new Label();
        this.idx = idx;
        categoryInput = new TextField();
        categoryInput.setPlaceholder("Caminho da Categoria");

        prepareLayout();
    }

    /**
     * Prepares CategoryRow Layout
     */
    private void prepareLayout() {
        setWidth(100, Unit.PERCENTAGE);
        categoryLbl.setValue("Categoria " + (idx + 1));

        categoryInput.setWidth(100, Unit.PERCENTAGE);
        addComponents(categoryLbl, categoryInput);
    }

    /**
     * Gets TextField Value
     * @return textfield value
     */
    protected String getInputValue() {
        return categoryInput.getValue();
    }
}
