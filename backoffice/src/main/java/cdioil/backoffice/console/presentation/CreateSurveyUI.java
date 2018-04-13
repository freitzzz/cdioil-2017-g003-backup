package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.CreateSurveyController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.*;
import cdioil.domain.authz.Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateSurveyUI {

    private CreateSurveyController controller;
    private Manager loggedManager;

    public CreateSurveyUI(Manager manager) {
        controller = new CreateSurveyController();
        menuLoop();
        loggedManager = manager;
    }

    private void menuLoop() {
        int option = 0;

        do {
            option = menuSurvey();
            List<Category> allCategories = loggedManager.categoriesFromManager();

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
                        System.out.println("All categories: \n");
                        for (int i = 0; i < allCategories.size(); i++) {
                            System.out.println((i + 1) + ". " + allCategories.get(i) + "\n");
                        }
                        int cat = Console.readInteger("Please choose a category: \n") - 1;

                        category = allCategories.get(cat);


                        questions = new ArrayList<>();

                        if (category != null) {
                            categories.add(category);
                            map.put(category, questions);

                            allQuestions = controller.questionsForCategory(category);

                            boolean flag;

                            do {
                                flag = false;
                                for (int i = 0; i < allQuestions.size(); i++) {
                                    System.out.println((i + 1) + ". " + allQuestions.get(i));
                                }
                                String[] chooseQuestions;
                                try {
                                    chooseQuestions = Console.readLine("Please insert the desired questions: (Separated by commas) \n").split(",");

                                    for (String s : chooseQuestions) {
                                        map.get(category).add(allQuestions.get(Integer.parseInt(s) - 1));
                                    }

                                } catch (IllegalArgumentException e) {
                                    flag = true;
                                    System.out.println("ERROR");
                                }

                            } while (flag);


                            System.out.println("Do you want to continue add more categories?");
                            categoryOption = menuYesNo();

                        } else {
                            System.out.println("ERROR : Incorrect path to category");
                        }


                    } while (categoryOption != 2);

                    if (category != null) {
                        System.out.println("Want to insert the data of when the survey ends? \n");
                        LocalDateTime date = dateMenu(menuYesNo());

                        System.out.println(controller.createSurvey(categories, date, map));
                    }

                    break;
                case 2:
                    List<Product> productsFound;
                    List<SurveyItem> allProducts = new ArrayList<>();
                    List<Question> questionsFound;
                    HashMap<SurveyItem, List<Question>> productMap = new HashMap<>();
                    Product product = null;
                    int optionProduct = -1;

                    do {
                        String productName = Console.readLine("\nPlease insert the Product name: \n");
                        productsFound = controller.findProducts(productName);

                        allQuestions = new ArrayList<>();

                        if (productsFound.size() > 1) {
                            for (int i = 0; i < productsFound.size(); i++) {
                                System.out.println((i + 1) + ". " + productsFound.get(i) + "\n");
                            }

                            product = productsFound.get(Console.readInteger("\nPlease choose a Product: \n") - 1);
                            allProducts.add(product);
                            productMap.put(product, allQuestions);

                        } else if (productsFound.size() == 1) {
                            product = productsFound.get(0);
                            allProducts.add(product);
                            productMap.put(product, allQuestions);
                        } else {
                            System.out.println("ERROR: Product not found");
                        }

                        boolean flagProducts;

                        do {
                            flagProducts = false;
                            try {
                                if (product != null) {
                                    questionsFound = controller.questionForProducts(product);

                                    for (int i = 0; i < questionsFound.size(); i++) {
                                        System.out.println((i + 1) + ". " + questionsFound.get(i));
                                    }
                                    String[] questionChoosen = Console.readLine("Please insert the desired questions: (Separated by commas) \n").split(",");

                                    for (String s : questionChoosen) {
                                        productMap.get(product).add(questionsFound.get(Integer.parseInt(s) - 1));
                                    }
                                }
                            } catch (IllegalArgumentException e) {
                                flagProducts = true;
                                System.out.println("ERROR: Please try again");
                            }
                        } while (flagProducts);

                        if (product != null) {

                            System.out.println("Do you want to continue add more products?");
                            optionProduct = menuYesNo();
                        }
                    } while (optionProduct != 2);

                    if (product != null) {
                        System.out.println("Want to insert the data of when the survey ends? \n");
                        LocalDateTime date = dateMenu(menuYesNo());

                        controller.createSurvey(allProducts, date, productMap);
                    }
                    break;
            }

        } while (option != 3);
    }


    private LocalDateTime dateMenu(int number) {
        LocalDateTime date = null;
        if (number == 1) {
            int day = Console.readInteger("Day: ");
            int month = Console.readInteger("\nMonth: ");
            int year = Console.readInteger("\nYear: ");
            int hour = Console.readInteger("\nHour: ");
            int min = Console.readInteger("\nMinutes: ");
            date = LocalDateTime.of(year, month, day, hour, min);
        }
        return date;
    }


    private int menuSurvey() {
        System.out.println("1. Survey for Categories\n" +
                "2. Survey for Products\n" +
                "3. Exit\n");

        return Console.readInteger("Please insert the desired option: \n");
    }

    private int menuYesNo() {
        return Console.readInteger("1. Yes\n" +
                "2. No\n");
    }
}
