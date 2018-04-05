package cdioil.frontoffice.presentation;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.EAN;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.Product;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.Question;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.frontoffice.utils.Console;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AnswerSurveyMain {

    public static void main(String[] args) {
        Product p = new Product("Red Wine", new EAN("123123332"));

        List<SurveyItem> itemList = Arrays.asList(p);
        Survey s = new Survey(itemList, LocalDateTime.now(), LocalDateTime.now());

        Question q1 =
                new BinaryQuestion("Did you enjoy this product?", "Q1");
        Question q2 =
                new QuantitativeQuestion("How much would you recommend this product to other people?",
                        "Q2", new Double(0), new Double(5), new Double(0.5));

        LinkedList<String> mpOptions = new LinkedList<>();
        mpOptions.add("Very Good");
        mpOptions.add("Good");
        mpOptions.add("Neutral");
        mpOptions.add("Bad");
        mpOptions.add("Very Bad");
        Question q3 =
                new MultipleChoiceQuestion("What was your overall experience with this product?",
                        "Q3", mpOptions);

        s.addQuestion(q1);
        s.addQuestion(q2);
        s.addQuestion(q3);

        System.out.println("=============================");
        System.out.println("       Answer Survey");
        System.out.println("=============================");

        System.out.println(p.toString());
        System.out.println("=============================");


        System.out.println(q1.toString());
        String a1 = Console.readLine("Answer (Yes/No): ");

        System.out.println(q2.toString());
        String a2 = Console.readLine("Answer (0-4):");

        System.out.println(q3.toString());
        System.out.println(mpOptions);
        String a3 = Console.readLine("Answer: ");

        System.out.println("=============================");
        System.out.println("         Summary");
        System.out.println("=============================");

        System.out.print("Q: " + q1.toString());
        System.out.println(a1);

        System.out.print("Q: " + q2.toString());
        System.out.println(a2);

        System.out.print("Q: " + q3.toString());
        System.out.println(a3);

        System.out.println("Do you wish to proceed?");
        Console.readLine("Yes or No?: ");

        System.out.println("=============================");
        System.out.println("  Thanks for your feedback!");
        System.out.println("=============================");
        Console.readLine("");
    }
}
