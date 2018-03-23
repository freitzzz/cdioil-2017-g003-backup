package cdioil.domain;

import cdioil.domain.authz.GrupoUtilizadores;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 * Represents a product survey
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Survey extends Event implements Serializable{

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
    private Produto product;
    
    /**
     * Data de realização do Survey.
     */
    @Temporal(TemporalType.DATE)
    private Calendar date;

    /**
     * Survey's questions.
     */
    private List<Question> questionList;

    /**
     * Builds an instance of survey with a product, date and targetAudience
     *
     * @param product product associated with the survey
     * @param date  date when the survey was done
     * @param targetAudience survey's target audience
     */
    public Survey(Produto product, Calendar date, GrupoUtilizadores targetAudience) {
        super(targetAudience);
        if (product == null) {
            throw new IllegalArgumentException("O inquérito tem que ter um produto");
        }
        this.product = product;
        this.questionList = new LinkedList<>();
        if (date == null) {
            throw new IllegalArgumentException("O inquérito tem que ter uma data");
        }
        this.date = date;
    }
    
    protected Survey(){
        //For ORM
    }

    /**
     * Adds a question to the list of questions.
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
     * @param question question to be verified
     * @return true, if it already exists in the list, false if otherwise
     */
    public boolean isValidQuestion(Question question) {
        return questionList.contains(question);
    }

    /**
     * Returns a description of the survey (product description and date)
     *
     * @return survey's description
     */
    @Override
    public String toString() {
        return "Inquerito sobre o produto:\n" + product.toString()
                + "\nData:\n" + date;
    }

    /**
     * Survey's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return product.hashCode() + questionList.hashCode();
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
        if (!this.product.equals(other.product)) {
            return false;
        }
        return this.questionList.equals(other.questionList);
    }

     /**
     * Returns the info of a Survey
     *
     * @return survey info
     */
    @Override
    public String info() {
        return toString();
    }
}
