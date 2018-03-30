package cdioil.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 * Represents a product survey
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Survey implements Serializable{

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @GeneratedValue
    /**
     * Database id.
     */
    private int id;

    /**
     * Product associated to the survey.
     */
    private List<SurveyItem> list;
    
    /**
     * Date of when the survey was done.
     */
    private LocalDateTime surveyDate;

    /**
     * ======================================
     * TODO Replace questionList with graph.
     * ======================================
     */
    @OneToMany
    private List<Question> questionList;

    /**
     * Builds an instance of survey with a product and a date
     *
     * @param list list of products or categories the survey is associated to
     * @param date  date when the survey was done
     */
    public Survey(List<SurveyItem> list, LocalDateTime date) {
        if (list == null) {
            throw new IllegalArgumentException("O inquérito tem que ter pelo menos"
                    + " um produto ou uma categoria");
        }
        this.list = list;
        this.questionList = new LinkedList<>(); // TODO Remove after
                                                // implementing graph
        if (date == null) {
            throw new IllegalArgumentException("O inquérito tem que ter uma data");
        }
        this.surveyDate = date;
    }
    
    protected Survey(){
        //For ORM
    }

    /**
     * Adds a question to the list of questions.
     *
     * ==============================================
     * TODO Delete method after graph has been added.
     * ==============================================
     * 
     * 
     * @param question question to be added
     * @return true, if the question is added, false if the question is invalid
     */
    public boolean addQuestion(Question question) {
        if (question == null || isValidQuestion(question)) {
            return false;
        }
        return questionList.add(question);
    }

    /**
     * Remove a question from the question list.
     *
     * ==============================================
     * TODO Delete method after graph has been added.
     * ==============================================
     * 
     * @param question question to be removed
     * @return true if the question was removed, false if otherwise
     */
    public boolean removeQuestion(Question question) {
        if (question == null || !isValidQuestion(question)) {
            return false;
        }
        return questionList.remove(question);
    }

    /**
     * Checks if a question already exists in the question list
     *
     * ==============================================
     * TODO Delete method after graph has been added.
     * ==============================================
     * 
     * @param question question to be verified
     * @return true, if it already exists in the list, false if otherwise
     */
    public boolean isValidQuestion(Question question) {
        return questionList.contains(question);
    }

    /**
     * Returns a description of the survey (product or category
     * description and date)
     *
     * @return survey's description
     */
    @Override
    public String toString() {
        return "Inquerito sobre:\n" + list.toString()
                + "\nData:\n" + surveyDate;
    }

    /**
     * Survey's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return list.hashCode() + questionList.hashCode();
    }

    /**
     * Checks if two Survey instances are the same by comparing their product
     * and question list.
     *
     * @param obj object to be compared
     * @return true if the instances are the same, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Survey)) {
            return false;
        }
        final Survey other = (Survey) obj;
        if (!this.list.equals(other.list)) {
            return false;
        }
        return this.questionList.equals(other.questionList); //TODO Remove
                                                             // after implementing graph
    }
}
