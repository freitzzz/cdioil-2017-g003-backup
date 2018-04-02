package cdioil.application.utils;

import cdioil.domain.Answer;
import cdioil.domain.Question;
import cdioil.domain.Review;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilitary class that writes survey answers into a CSV file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class CSVSurveyAnswersWriter implements SurveyAnswersWriter{
    /**
     * Constant that represents the label used for the question identifier on the CSV file
     */
    private static final String QUESTION_LABEL="Questão";
    /**
     * Constant that represents the label used for the answer identifier on the CSV file
     */
    private static final String ANSWER_LABEL="Resposta";
    /**
     * Constant that represents the delimiter use for the CSV file (UTF-8 semi-colon like)
     */
    private static final char CSV_DELIMITER=';';
    /**
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * List with all survey reviews being exported
     */
    private final List<Review> surveyReviews;
    /**
     * Builds a new CSVSurveyAnswersWriter with the file that is going to be 
     * written all survey answers
     * @param filename String with the filename that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     */
    public CSVSurveyAnswersWriter(String filename,List<Review> surveyReviews){
        this.file=new File(filename);
        this.surveyReviews=surveyReviews;
    }
    /**
     * Method that writes all answers from a certain survey into a CSV file
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
        Map<Question,List<Answer>> mapQuestionAnswers=allQuestionsPerAnswers();
        List<String> csvContent=new ArrayList<>();
        String header=QUESTION_LABEL+CSV_DELIMITER+ANSWER_LABEL;
        csvContent.add(header);
        mapQuestionAnswers.forEach((question,answers)->{
            answers.forEach((answer)->{
                csvContent.add(question.content().toString()+CSV_DELIMITER+answer.content().toString());
            });
        });
        return FileWriter.writeFile(file,csvContent);
    }
    /**
     * Method that returns a map with all answers given for each question
     * @return Map with all answers given for each question
     */
    private Map<Question,List<Answer>> allQuestionsPerAnswers(){
        Map<Question,List<Answer>> mapQuestionAnswers=new HashMap<>();
        surveyReviews.forEach((t)->{
            Map<Question,Answer> surveyAnswers=t.getReviewQuestionAnswers();
            surveyAnswers.forEach((question,answer)->{
                List<Answer> questionAnswers=mapQuestionAnswers.get(question);
                if(questionAnswers==null){
                    questionAnswers=new ArrayList<>();
                    mapQuestionAnswers.put(question,questionAnswers);
                }
                questionAnswers.add(answer);
            });
        });
        return mapQuestionAnswers;
    }
}
