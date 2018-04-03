/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.application.authz.CategoryManagementController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.authz.Manager;

/**
 * User Interface for the User Story 150 - Add Categories to a Manager.
 *
 * @author Rita Gonçalves (1160912)
 */
public class AssociateCategoriesUI {

    /**
     * Separator used for clarity.
     */
    private static final String SEPARATOR = "=============================";

    /**
     * Message to indicate the list of all managers being presented.
     */
    private static final String MANAGER_LIST = "Lista de Todos Os Gestores\n";

    /**
     * Message to indicate that a manager must be picked.
     */
    private static final String PICK_MANAGER = "Insira o e-mail do gestor para "
            + "adicionar categorias:\n";

    /**
     * Message to indicate that the admin must insert a category identifier.
     */
    private static final String CATEGORY_ID_MESSAGE = "Insira o descritivo da(s) "
            + "categoria(s) que quer adicionar ao gestor selecionado:\n";

    /**
     * Error message for emails that are written incorrectly or don't exist.
     */
    private static final String EMAIL_ERROR = "O e-mail que inseriu não pertence"
            + " a um gestor/contém erros. Por favor, tente novamente.\n";

    /**
     * Error message for categories that don't exist or that the manager already is associated to it.
     */
    private static final String CATEGORY_ERROR = "O descritivo que inseriu não "
            + "corresponde a nenhuma categoria ou a categoria já está associada"
            + " a este gestor.\nPor favor, tente novamente.\n";

    /**
     * Success message indicating that the categories were successfully associated to the manager.
     */
    private static final String SUCCESS_MESSAGE = "\nAs categorias foram adicionadas"
            + " com sucesso.\n";

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the domain classes.
     */
    private CategoryManagementController ctrl;

    /**
     * Creates a new User Interface.
     */
    public AssociateCategoriesUI() {
        ctrl = new CategoryManagementController();
        doShow();
    }

    /**
     * Method that runs US150 - Add Categories to a Manager.
     */
    public void doShow() {
  Iterable<Manager> managerList = ctrl.listAllManagers();
        boolean emailFlag = false;
        boolean categoryFlag = false;
        System.out.println(SEPARATOR);
        System.out.println(MANAGER_LIST);
        managerList.forEach((t) -> {
            System.out.println(t.toString());
        });
        System.out.println(SEPARATOR);
        while (!emailFlag) {
            try {
                if (ctrl.setManager(Console.readLine(PICK_MANAGER))) {
                    System.out.println(SEPARATOR);
                    emailFlag = true;
                    while (!categoryFlag) {
                        if (ctrl.addCategories(Console.readLine(CATEGORY_ID_MESSAGE))) {
                            System.out.println(SUCCESS_MESSAGE);
                            categoryFlag = true;
                        } else {
                            System.out.println(CATEGORY_ERROR);
                        }
                    }
                } else {
                    System.out.println(EMAIL_ERROR);
                }
                System.out.println(SEPARATOR);
            } catch (IllegalArgumentException e) {
                System.out.println(EMAIL_ERROR);
            }
        }
    }
}
