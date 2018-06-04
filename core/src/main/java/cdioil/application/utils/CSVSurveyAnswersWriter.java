package cdioil.application.utils;

import cdioil.files.FileWriter;
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
    private static final String ANSWER_LABEL="Respostas";
    /**
     * Constant that represents the label used for suggestion identified on the CSV file
     */
    private static final String SUGGESTION_LABEL="Sugestões";
    /**
     * Constant that represents the label being used for the Survey Informations header
     */
    private static final String SURVEY_INFORMATIONS_HEADER="Informações Inquérito";
    /**
     * Constant that represents the label being used for the survey review count identified on the CSV file
     */
    private static final String SURVEY_REVIEW_COUNT="NºAvaliações:";
    /**
     * Constant that represents the number of lines used to space different information about the reviews
     */
    private static final int CSV_LINES_SPACING=3;
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
        csvContent.addAll(prepareCSVHeader());
        for(int i=0;i<CSV_LINES_SPACING;i++)csvContent.add(""+CSV_DELIMITER);
        csvContent.addAll(prepareReviewSuggestionsListing());
        for(int i=0;i<CSV_LINES_SPACING;i++)csvContent.add(""+CSV_DELIMITER);
        String header=QUESTION_LABEL+CSV_DELIMITER+ANSWER_LABEL;
        csvContent.add(header);
        mapQuestionAnswers.forEach((question,answers)->{
            csvContent.add(question.toString());
            answers.forEach(answer->{
                csvContent.add(CSV_DELIMITER+answer.toString());
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
        surveyReviews.forEach(review->{
            Map<Question,Answer> surveyAnswers=review.getReviewQuestionAnswers();
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
    /**
     * Prepares the CSV header containing the survey information
     * @return List with the CSV header used containing the survey information
     */
    private List<String> prepareCSVHeader(){
        List<String> csvHeader=new ArrayList<>();
        csvHeader.add(SURVEY_INFORMATIONS_HEADER);
        csvHeader.add(SURVEY_REVIEW_COUNT+CSV_DELIMITER+surveyReviews.size());
        return csvHeader;
    }
    /**
     * Prepares the reviews suggestions listing
     * @return List with the reviews suggestions prepared for the CSV file on a listing mode
     */
    private List<String> prepareReviewSuggestionsListing(){
        List<String> csvReviewSuggestions=new ArrayList<>();
        csvReviewSuggestions.add(SUGGESTION_LABEL);
        for(int i=0;i<surveyReviews.size();i++){
            if(surveyReviews.get(i).getSuggestion()!=null){
                csvReviewSuggestions.add(surveyReviews.get(i).getSuggestion());
            }
        }
        return csvReviewSuggestions;
    }
}
