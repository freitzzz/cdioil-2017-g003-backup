package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.CreateSurveyController;
import cdioil.console.Console;
import cdioil.domain.*;
import cdioil.domain.authz.*;

import java.time.LocalDateTime;
import java.util.*;

public class CreateSurveyUI {

    private CreateSurveyController controller;
    private Manager loggedManager;

    public CreateSurveyUI(Manager manager) {
        controller = new CreateSurveyController();
        loggedManager = manager;
        menuLoop();
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
                    List<Template> templates = new ArrayList<>();
                    List<Template> allTemplates;
                    int templateOption;
                    int categoryQuestionsOption;
                    int independentQuestionOption;
                    UsersGroup usersGroup = null;

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
                            // TEMPLATES
                            System.out.println("Would you like to choose a template?");
                            templateOption = menuYesNo();

                            if (templateOption == 1) {
                                allTemplates = controller.templatesForCategory(category);

                                boolean flagTemplates;

                                do {
                                    flagTemplates = false;
                                    for (int i = 0; i < allTemplates.size(); i++) {
                                        System.out.println((i + 1) + ". " + allTemplates.get(i));
                                    }
                                    try {
                                        String[] templatesChoice = Console.readLine("Please choose the template/s: (Separated by commas)\n").split(",");

                                        for (String s : templatesChoice) {
                                            templates.add(allTemplates.get(Integer.parseInt(s) - 1));
                                        }
                                    } catch (IllegalArgumentException e) {
                                        flagTemplates = true;
                                        System.out.println("ERROR");
                                    }
                                } while (flagTemplates);

                            }
                            //CATEGORY QUESTIONS

                            System.out.println("Would you like to choose a question/s for the category? \n");
                            categoryQuestionsOption = menuYesNo();

                            if (categoryQuestionsOption == 1) {
                                allQuestions = controller.questionsForCategory(category);

                                boolean flag;

                                do {
                                    flag = false;
                                    for (int i = 0; i < allQuestions.size(); i++) {
                                        System.out.println((i + 1) + ". " + allQuestions.get(i) + "\n");
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
                            }

                            //Independent Questions
                            System.out.println("Would you like to choose a question?\n");
                            independentQuestionOption = menuYesNo();
                            if (independentQuestionOption == 1) {
                                independentQuestions(category, map);
                            }

                            System.out.println("Do you want to continue add more categories?");
                            categoryOption = menuYesNo();

                        } else {
                            System.out.println("ERROR : Incorrect path to category");
                        }


                    } while (categoryOption != 2);

                    LocalDateTime startDate = null;
                    LocalDateTime endDate = null;

                    if (category != null) {
                        System.out.println("Please choose the date of beginning of the survey");
                        startDate = dateMenu(1);
                        System.out.println("Want to insert the data of when the survey ends? \n");
                        endDate = dateMenu(menuYesNo());
                    }

                    System.out.println("Would you like to add a target audience?\n");
                    int targetAudienceOption = menuYesNo();
                    usersGroup = targetAudience(targetAudienceOption);

                    controller.createSurvey(categories, startDate, endDate, map, usersGroup);
                    

                    break;
                case 2:
                    List<Product> productsFound;
                    List<SurveyItem> allProducts = new ArrayList<>();
                    List<Question> questionsFound;
                    HashMap<SurveyItem, List<Question>> productMap = new HashMap<>();
                    Product product = null;
                    int optionProduct = -1;
                    int productQuestionsOption;
                    int productIndependentQuestionsOption;
                    UsersGroup productUsersGroup = null;

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

                        System.out.println("Would you like to choose a question/s for the Product? \n");
                        productQuestionsOption = menuYesNo();

                        if (productQuestionsOption == 1) {
                            do {
                                flagProducts = false;
                                try {
                                    if (product != null) {
                                        questionsFound = controller.questionForProducts(product);

                                        for (int i = 0; i < questionsFound.size(); i++) {
                                            System.out.println((i + 1) + ". " + questionsFound.get(i));
                                        }
                                        String[] questionChosen = Console.readLine("Please insert the desired questions: (Separated by commas) \n").split(",");

                                        for (String s : questionChosen) {
                                            productMap.get(product).add(questionsFound.get(Integer.parseInt(s) - 1));
                                        }
                                    }
                                } catch (IllegalArgumentException e) {
                                    flagProducts = true;
                                    System.out.println("ERROR: Please try again");
                                }
                            } while (flagProducts);
                        }

                        System.out.println("Would you like to add independent question? \n");
                        productIndependentQuestionsOption = menuYesNo();

                        if (productIndependentQuestionsOption == 1) {
                            independentQuestions(product, productMap);
                        }

                        if (product != null) {

                            System.out.println("Do you want to continue add more products?");
                            optionProduct = menuYesNo();
                        }
                    } while (optionProduct != 2);

                    LocalDateTime beginingDate = null;
                    LocalDateTime endingDate = null;

                    if (product != null) {
                        System.out.println("Please choose the date of beginning of the survey: \n");
                        beginingDate = dateMenu(1);
                        System.out.println("Want to insert the data of when the survey ends? \n");
                        endingDate = dateMenu(menuYesNo());
                    }

                    System.out.println("Would you like to add a target audience?\n");
                    int productTargetAudienceOption = menuYesNo();
                    productUsersGroup = targetAudience(productTargetAudienceOption);

                    controller.createSurvey(allProducts, beginingDate, endingDate, productMap, productUsersGroup);

                    break;
            }

        } while (option != 3);
    }

    private void independentQuestions(SurveyItem surveyItem, HashMap<SurveyItem, List<Question>> map) {
        List<Question> indQuestions = controller.independantQuestions();

        for (int i = 0; i < indQuestions.size(); i++) {
            System.out.println((i + 1) + ". " + indQuestions.get(i) + "\n");
        }

        try {
            String[] choices = Console.readLine("Please select the questions (Separated by commas)").split(",");

            for (String s : choices) {
                map.get(surveyItem).add(indQuestions.get(Integer.parseInt(s) - 1));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: couldn't add independent questions");
        }
    }

    private UsersGroup targetAudience(int targetAudienceOption) {
        UsersGroup usersGroup = null;
        if (targetAudienceOption == 1) {
            Iterator allUsers = controller.findAll().iterator();
            LinkedList<RegisteredUser> userList = new LinkedList<>();
            usersGroup = new UsersGroup(loggedManager);
            while (allUsers.hasNext()) {
                userList.add((RegisteredUser) allUsers.next());
            }

            for (int i = 0; i < userList.size(); i++) {
                System.out.println((i+1) + ". "+ userList.get(i) + "\n");
            }

            try {
                String[] usersChoosen = Console.readLine("Please insert the numbers of the users: (Separated by commas) \n").split(",");

                for (String s : usersChoosen) {
                    usersGroup.addUser(userList.get(Integer.parseInt(s) - 1));
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: please try again");
            }
        }
        return usersGroup;
    }


    private LocalDateTime dateMenu(int number) {
        LocalDateTime date = LocalDateTime.MAX;
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
