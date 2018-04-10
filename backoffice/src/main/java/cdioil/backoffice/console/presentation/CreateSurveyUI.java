package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.CreateSurveyController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.*;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cdioil.domain.authz.Manager_.categories;

public class CreateSurveyUI {

    /**
     * Sufix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_PREFIX = "^*";

    /**
     * Prefix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_SUFIX = "*$";

    private CreateSurveyController controller;

    public CreateSurveyUI() {
        controller = new CreateSurveyController();
        menuLoop();
    }

    private void interactionWithUser() {

    }

    private void menuLoop() {
        int option = 0;
        GlobalLibraryRepositoryImpl repository = new GlobalLibraryRepositoryImpl();
        GlobalLibrary globalLibrary = repository.findGlobalLibrary();
//        globalLibrary.getCatQuestionsLibrary().getCategories();



        do {
            option = menuSurvey();

            switch (option) {
                case 0:
                    break;
                case 1:
                    int categoryOption = -1;
                    Category category;
                    List<Question> allQuestions;
                    List<Question> questions;
                    HashMap<SurveyItem, List<Question>> map = new HashMap<>();
                    List<SurveyItem> categories = new ArrayList<>();

                    do {
                        questions = new ArrayList<>();

                        String path = Console.readLine("Please insert the path to the category");
                        category = controller.findCategory(path);


                        if (category != null) {
                            categories.add(category);
                            map.put(category, questions);

                            allQuestions = controller.questionsForCategory(category);

                            for (int i = 0; i < allQuestions.size(); i++) {
                                System.out.println((i + 1) + ". " + allQuestions.get(i));
                            }

                            String[] chooseQuestions = Console.readLine("Please insert the desired questions: (Separated by commas) \n").split(",");

                            for (String s : chooseQuestions) {
                                map.get(category).add(allQuestions.get(Integer.parseInt(s)));
                            }

                            System.out.println("Do you want to continue?");
                            categoryOption = menuYesNo();

                        } else {
                            System.out.println("ERROR : Incorrect path to category");
                        }


                    } while (categoryOption != 2);


                    System.out.println("Want to insert the data of when the survey ends? \n");
                    LocalDateTime date = null;
                    if (menuYesNo() == 1) {
                        int day = Console.readInteger("Day: ");
                        int month = Console.readInteger("\nMonth: ");
                        int year = Console.readInteger("\nYear: ");
                        int hour = Console.readInteger("\nHour: ");
                        int min = Console.readInteger("\nMinutes: ");
                        date = LocalDateTime.of(year, month, day, hour, min);
                    }

                    System.out.println(controller.createSurvey(categories, date, map));
                    break;
                case 2:



                    break;
                default:
                    System.out.println();
                    break;
            }
        } while (option != 0);
    }

    private int menuSurvey() {
        System.out.println("1. Survey for Categories\n" +
                "2. Survey for Products\n");

        return Console.readInteger("Please insert the desired option: \n");
    }

    private int menuYesNo() {
        return Console.readInteger("1. Yes\n" +
                "2. No\n");
    }
}
