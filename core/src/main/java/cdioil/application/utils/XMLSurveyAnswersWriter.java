/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Answer;
import cdioil.domain.Question;
import cdioil.domain.Review;
import cdioil.files.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilitary class that writes survey answers into a XML file
 *
 * @author Ana Guerra (1161191)
 */
public class XMLSurveyAnswersWriter implements SurveyAnswersWriter {

    private static final String OP_LABEL = "<";
    private static final String CL_LABEL = ">";
    private static final String OPCL_LABEL = "</";
    private static final String ID_LABEL = " ID= ";
    private static final String TIPE = "\t";
    private static final char ASPAS = '"';
    private static final String SPACE = " ";
    private static final String IGUAL = "= ";
    /**
     * Constant that represents the label used for the survey identifier on the XML file
     */
    private static final String SURVEY_LABEL = "Inquerito";
    /**
     * Constant that represents the label used for the question identifier on the XML file
     */
    private static final String QUESTION_LABEL = "Questao";
    /**
     * Constant that represents the label used for the type of question identifier on the XML file
     */
    private static final String TYPE_LABEL = "Tipo";
    /**
     * Constant that represents the label used for the answers identifier on the XML file
     */
    private static final String ANSWERS_LABEL = "Respostas";
    /**
     * Constant that represents the label used for the answer identifier on the XML file
     */
    private static final String ANSWER_LABEL = "Resposta";
    /**
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * List with all survey reviews being exported
     */
    private final List<Review> surveyReviews;

    /**
     * Builds a new XMLSurveyAnswersWriter with the file that is going to be written all survey answers
     *
     * @param filename String with the filename that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     */
    public XMLSurveyAnswersWriter(String filename, List<Review> surveyReviews) {
        this.file = new File(filename);
        this.surveyReviews = surveyReviews;
    }

    /**
     * Method that writes all answers from a certain survey into a XML file
     *
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
        Map<Question, List<Answer>> mapQuestionAnswers = allQuestionsPerAnswers();
        List<String> xmlContent = new ArrayList<>();
        for (Question question : mapQuestionAnswers.keySet()) {
            xmlContent.add(OP_LABEL + QUESTION_LABEL + ID_LABEL + ASPAS +question.getQuestionID() + ASPAS + SPACE + CL_LABEL);
            xmlContent.add(TIPE+OP_LABEL + TYPE_LABEL + SPACE + TYPE_LABEL + IGUAL + ASPAS + question.getType() + ASPAS + SPACE + CL_LABEL);
            xmlContent.add(TIPE+TIPE+OP_LABEL + ANSWERS_LABEL + CL_LABEL);
            for (Answer answer : mapQuestionAnswers.get(question)) {
                xmlContent.add(TIPE+TIPE+TIPE+OP_LABEL + ANSWER_LABEL + CL_LABEL +
                        SPACE +answer.toString()+ SPACE + OPCL_LABEL + ANSWER_LABEL + CL_LABEL);
            }
            xmlContent.add(TIPE+TIPE+OPCL_LABEL + ANSWERS_LABEL + CL_LABEL);
            xmlContent.add(TIPE+OPCL_LABEL + TYPE_LABEL + CL_LABEL);
            xmlContent.add(OPCL_LABEL + QUESTION_LABEL + CL_LABEL);
        }
        return FileWriter.writeFile(file, xmlContent);
    }
    /**
     * Method that returns a map with all answers given for each question
     *
     * @return Map with all answers given for each question
     */
    private Map<Question, List<Answer>> allQuestionsPerAnswers() {
        Map<Question, List<Answer>> mapQuestionAnswers = new HashMap<>();
        surveyReviews.forEach(t -> {
            Map<Question, Answer> surveyAnswers = t.getReviewQuestionAnswers();
            surveyAnswers.forEach((question, answer) -> {
                List<Answer> questionAnswers = mapQuestionAnswers.get(question);
                if (questionAnswers == null) {
                    questionAnswers = new ArrayList<>();
                    mapQuestionAnswers.put(question, questionAnswers);
                }
                questionAnswers.add(answer);
            });
        });
        return mapQuestionAnswers;
    }

}
