package cdioil.backoffice.webapp.manager;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import server.droporchoose.UploadComponent;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class ManagerImportView extends ManagerImportDesign implements View{
    /**
     * Constant that represents the number of rows that the grid layout has
     */
    private static final int GRID_LAYOUT_IMPORTAR_ROWS=4;
    /**
     * Constant that represents the number of columns that the grid layout has
     */
    private static final int GRID_LAYOUT_IMPORTAR_COLUMNS=3;
    /**
     * Constant that represents the column of the upload component
     */
    private static final int UPLOAD_COMPONENT_COLUMN=1;
    /**
     * Constant that represents the row of the upload component
     */
    private static final int UPLOAD_COMPONENT_ROW=2;
    /**
     * Constant that represents the width of the upload component
     */
    private static final float UPLOAD_COMPONENT_WIDTH=385f;
    /**
     * Constant that represents the height of the upload component
     */
    private static final float UPLOAD_COMPONENT_HEIGHT=220f;
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Manager Import Panel";
    /**
     * Constant that represents the "drop or choose" file uploader description
     */
    private static final String DROP_OR_CHOOSE_DESCRIPTION="Large ou";
    /**
     * Constant that represents the "choose a file" file uploader description
     */
    private static final String CHOOSE_DESCRIPTION="Escolha um ficheiro";
    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Current UploadComponent
     */
    private UploadComponent currentUploadComponent;
    /**
     * Builds a new ManagerImportView
     */
    public ManagerImportView(){
        navigator= UI.getCurrent().getNavigator();
        configuration();
    }
    /**
     * Configures current page
     */
    private void configuration(){
        configureGridLayout();
        configureImportarQuestoesCategoriasButton();
    }

    /**
     * Configures Importar Questoes Categorias button
     */
    private void configureImportarQuestoesCategoriasButton(){
        btnImportarQuestoesCategorias.addClickListener(clickEvent -> {
            configureUploaderComponent();
        });
    }

    /**
     * Configures current uploader component
     */
    private void configureUploaderComponent(){
        if(this.currentUploadComponent==null){
            gridLayoutImportar.newLine();
            gridLayoutImportar.addComponent(
                    this.currentUploadComponent=createUploadComponent(),UPLOAD_COMPONENT_COLUMN,UPLOAD_COMPONENT_ROW);
            gridLayoutImportar.setComponentAlignment(this.currentUploadComponent,Alignment.BOTTOM_CENTER);
        }else{
            gridLayoutImportar.removeComponent(this.currentUploadComponent);
            this.currentUploadComponent=null;
        }
    }

    /**
     * Creates a personal "drop or choose" file uploader
     * @return UploadComponent with the personal upload component
     */
    private UploadComponent createUploadComponent(){
        UploadComponent uploadComponent=new UploadComponent();
        uploadComponent.getDropTextLabel().setValue(DROP_OR_CHOOSE_DESCRIPTION);
        uploadComponent.getChoose().setButtonCaption(CHOOSE_DESCRIPTION);
        uploadComponent.setWidth(UPLOAD_COMPONENT_WIDTH,Unit.PIXELS);
        uploadComponent.setHeight(UPLOAD_COMPONENT_HEIGHT,Unit.PIXELS);
        //TO-DO: Implement callback function for storing upload file
        return uploadComponent;
    }

    /**
     * Configures the panel grid layout
     */
    private void configureGridLayout(){
        this.gridLayoutImportar.setRows(GRID_LAYOUT_IMPORTAR_ROWS);
        this.gridLayoutImportar.setColumns(GRID_LAYOUT_IMPORTAR_COLUMNS);
    }
}
