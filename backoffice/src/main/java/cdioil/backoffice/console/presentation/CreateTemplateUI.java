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
        Category category;
        List<Question> categoryQuestions;
        List<Question> questionsChosen = new ArrayList<>();
        List<Question> independentQuestions;
        int menuCategory;
        int menuIndependent;
        QuestionGroup questionGroup;
        String title;

        System.out.println("Categories available: \n");

        for(int i = 0; i < categoriesForManager.size(); i++) {
            System.out.println((i + 1) + ". " + categoriesForManager.get(i) + "\n");
        }

        int catChosen = Console.readInteger("Please choose a category: ");
        category = categoriesForManager.get(catChosen - 1);

        do {
            System.out.println("Would you like to add questions of the category to the template?\n");
            menuCategory = menuYesNo();

            if (menuCategory == 1) {
                System.out.println("\nQuestions for the category " + category.toString() + ": \n");

                categoryQuestions = controller.listQuestionsForCategory(category);

                for (int i = 0; i < categoryQuestions.size(); i++) {
                    System.out.println((i + 1) + ". " + categoryQuestions.get(i) + "\n");
                }

                boolean flagCategoryQuestions;
                do {
                    flagCategoryQuestions = false;
                    try {
                        String[] catQuestionsChosen = Console.readLine("Please choose the questions of the category (Separated by commas): \n").split(",");

                        for (String question : catQuestionsChosen) {
                            questionsChosen.add(categoryQuestions.get(Integer.parseInt(question) - 1));
                        }

                    } catch (IllegalArgumentException exception) {
                        flagCategoryQuestions = true;
                        System.out.println("ERROR: Please try again");
                    }
                } while (flagCategoryQuestions);
            }

            System.out.println("Would you like to add independent questions to the template?\n");
            menuIndependent = menuYesNo();

            if (menuIndependent == 1) {
                System.out.println("Independent Questions: \n");

                independentQuestions = controller.listIndependentQuestions();

                for (int i = 0; i < independentQuestions.size(); i++) {
                    System.out.println((i + 1) + ". " + independentQuestions.get(i) + "\n");
                }

                boolean flagIndependentQuestions;
                do {
                    flagIndependentQuestions = false;
                    try {
                        String[] independentQuestionsChosen = Console.readLine("Please choose the questions (Separated by commas): \n").split(",");

                        for (String question : independentQuestionsChosen) {
                            questionsChosen.add(independentQuestions.get(Integer.parseInt(question) - 1));
                        }

                    } catch (IllegalArgumentException exception) {
                        flagIndependentQuestions = true;
                        System.out.println("ERROR: Please try again");
                    }
                } while (flagIndependentQuestions);
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
        return Console.readInteger("1. Yes\n" +
                "2. No\n");
    }
}
