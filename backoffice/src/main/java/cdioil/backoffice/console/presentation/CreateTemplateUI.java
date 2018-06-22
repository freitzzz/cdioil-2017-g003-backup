package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.CreateTemplateController;
import cdioil.console.Console;
import cdioil.domain.Category;
import cdioil.domain.Question;
import cdioil.domain.QuestionGroup;
import cdioil.domain.authz.Manager;

import java.util.ArrayList;
import java.util.List;

public class CreateTemplateUI {

    public CreateTemplateUI(Manager manager) {
        mainCreateTemplateUI(manager);
    }

    public void mainCreateTemplateUI(Manager manager) {
        CreateTemplateController controller = new CreateTemplateController();
        List<Category> categoriesForManager = controller.listAllCategoriesForManager(manager);
        Category category = null;
        List<Question> categoryQuestions;
        List<Question> questionsChosen = new ArrayList<>();
        List<Question> independentQuestions;
        int menuCategory = -1;
        int menuIndependent = -1;
        QuestionGroup questionGroup;
        String title;
        //new just because
        int out = 0;

        if (categoriesForManager.isEmpty()) {
            System.out.println("O gestor não tem categorias");
            return;
        }

        System.out.println("Categories available: \n");

        for (int i = 0; i < categoriesForManager.size(); i++) {
            System.out.println((i + 1) + ". " + categoriesForManager.get(i) + "\n");
        }

        int catChosen = Console.readInteger("Please choose a category: (SELECT THE NUMBER)");
        category = categoriesForManager.get(catChosen - 1);

        do {
            while (menuCategory == -1) {
                System.out.println("Would you like to add questions of the category to the template? (SELECT THE NUMBER)\n");
                menuCategory = menuYesNo();
            }


            if (menuCategory == 1) {
                System.out.println("\nQuestions for the category " + category.toString() + ": \n");

                categoryQuestions = controller.listQuestionsForCategory(category);

                if (categoryQuestions.size() != 0) {
                    for (int i = 0; i < categoryQuestions.size(); i++) {
                        System.out.println((i + 1) + ". " + categoryQuestions.get(i) + "\n");
                    }

                    boolean flagCategoryQuestions;
                    do {
                        flagCategoryQuestions = false;
                        String insertedString = Console.readLine("Please choose the questions of the category (Separated by commas): (SELECT THE NUMBERS)\n");

                        if (insertedString.contains(",")) {
                            String[] catQuestionsChosen = insertedString.split(",");

                            try {
                                for (int i = 0; i < catQuestionsChosen.length; i++) {
                                    questionsChosen.add(categoryQuestions.get(i));
                                }
                            } catch (IndexOutOfBoundsException ex) {
                                flagCategoryQuestions = true;
                                System.out.println("ERROR: Please choose within the range presented\n");
                            }


                        } else {
                            questionsChosen.add(categoryQuestions.get(Integer.parseInt(insertedString) - 1));
                        }
                    } while (flagCategoryQuestions);

                } else {
                    System.out.println("Category doesn't have questions");
                }

            }

            while (menuIndependent == -1) {
                System.out.println("Would you like to add independent questions to the template? (SELECT THE NUMBER)\n");
                menuIndependent = menuYesNo();

            }

            if (menuIndependent == 1) {
                System.out.println("Independent Questions: \n");

                independentQuestions = controller.listIndependentQuestions();

                if (independentQuestions.size() != 0) {
                    for (int i = 0; i < independentQuestions.size(); i++) {
                        System.out.println((i + 1) + ". " + independentQuestions.get(i) + "\n");
                    }

                    boolean flagIndependentQuestions;
                    do {
                        flagIndependentQuestions = false;
                        try {
                            String[] independentQuestionsChosen = Console.readLine("Please choose the questions (Separated by commas): (SELECT THE NUMBERS)\n").split(",");
                            try {
                                for (String question : independentQuestionsChosen) {
                                    questionsChosen.add(independentQuestions.get(Integer.parseInt(question) - 1));
                                }
                            } catch (IndexOutOfBoundsException ex) {
                                flagIndependentQuestions = true;
                                System.out.println("ERROR: Please choose within the range presented\n");
                            }

                        } catch (IllegalArgumentException exception) {
                            flagIndependentQuestions = true;
                            System.out.println("ERROR: Please try again\n");
                        }
                    } while (flagIndependentQuestions);
                } else {
                    System.out.println("There are no independent questions available");
                }

            }
        } while (menuCategory == 2 && menuIndependent == 2);


        title = Console.readLine("Please insert the name of the Template\n");

        questionGroup = new QuestionGroup(title);

        for (Question question : questionsChosen) {
            questionGroup.addQuestion(question);
        }

        if (controller.createTemplate(category, title, questionGroup)) {
            System.out.println("The template was created with success");
        } else {
            System.out.println("Couldn't create template");
        }

    }


    private int menuYesNo() {
        int response = -1;
        try {
            response = Integer.parseInt(Console.readLine("1. Yes\n"
                    + "2. No\n"));
        } catch (NumberFormatException ex) {
            System.out.println("Please select the number of the option");
        }

        return response;
    }
}
