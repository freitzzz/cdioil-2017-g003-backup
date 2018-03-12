package eapli.demoapplication.backoffice.consoleapp.presentation.meals;

import eapli.demoapplication.application.meals.ActivateDeactivateDishTypeController;
import eapli.demoapplication.domain.meals.DishType;
import eapli.framework.application.Controller;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MCN on 29/03/2016.
 */
public class ActivateDeactivateDishTypeUI extends AbstractUI {

    private final ActivateDeactivateDishTypeController theController = new ActivateDeactivateDishTypeController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {

        final Iterable<DishType> allDishTypes = this.theController.allDishTypes();
        if (!allDishTypes.iterator().hasNext()) {
            System.out.println("There is no registered Dish Type");
        } else {
            final SelectWidget<DishType> selector = new SelectWidget<>(allDishTypes, new DishTypePrinter());
            selector.show();
            final DishType updtDishType = selector.selectedElement();
            try {
                this.theController.changeDishTypeState(updtDishType);
            } catch (DataConcurrencyException ex) {
                Logger.getLogger(ActivateDeactivateDishTypeUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DataIntegrityViolationException ex) {
                Logger.getLogger(ActivateDeactivateDishTypeUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "Activate / Deactivate Dish Types";
    }
}
