package cdioil.backoffice.webapp.utils;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

import java.io.File;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public final class ImageUtils {
    /**
     * Constant that represents the current base root path where Vaadin componments are placed
     * <br>Points to webapp folder
     */
    private static final String BASE_ROOT_PATH=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    /**
     * Method that creates a file resource that contains a certain image
     * @param imagePath String with the image path
     * @return FileResource with the resource that contains the image located on a certain path
     */
    public static FileResource imagePathAsResource(String imagePath){
        return new FileResource(new File(BASE_ROOT_PATH+imagePath));
    }

    /**
     * Hides default constructor
     */
    private ImageUtils(){}
}
