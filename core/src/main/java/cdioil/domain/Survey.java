package cdioil.domain;

import cdioil.application.utils.Graph;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

/**
 * Represents a survey
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Survey implements Serializable {

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
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<SurveyItem> itemList;

    /**
     * Date of when the survey was done.
     */
    private LocalDateTime surveyDate;

    /**
     * Date of when the survey ends.
     */
    private LocalDateTime endingDate;

    /**
     * Question and Answer graph.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private Graph graph;

    /**
     * Survey's state.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @Enumerated
    private SurveyState state;

    /**
     * Builds an instance of survey with a product and a date.
     *
     * @param itemList list of products or categories the survey is associated
     * to
     * @param date date when the survey was done
     * @param endingDate date of when the survey is end
     */
    public Survey(List<SurveyItem> itemList, LocalDateTime date, LocalDateTime endingDate) {
        if (itemList == null) {
            throw new IllegalArgumentException("O inquérito tem que ter pelo menos"
                    + " um produto ou uma categoria");
        }
        if (date == null) {
            throw new IllegalArgumentException("O inquérito tem que ter uma data");
        }
        this.itemList = itemList;
        this.graph = new Graph();
        this.surveyDate = date;
        this.endingDate = endingDate;
        this.state = SurveyState.DRAFT;
    }

    protected Survey() {
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
        return graph.insertVertex(question);
    }

    /**
     * Remove a question from the graph.
     *
     * @param question question to be removed
     * @return true if the question was removed, false if otherwise
     */
    public boolean removeQuestion(Question question) {
        if (question == null || !isValidQuestion(question)) {
            return false;
        }
        return graph.removeVertex(question);
    }

    /**
     * Checks if a question already exists in the graph.
     *
     * @param question question to be verified
     * @return true, if it already exists in the list, false if otherwise
     */
    public boolean isValidQuestion(Question question) {
        return graph.vertexExists(question);
    }

    /**
     * Changes the state of a survey
     *
     * @param newState new state of the survey
     * @return true if the state was modified, false if otherwise
     */
    public boolean changeState(SurveyState newState) {
        if (newState != null && !state.equals(newState)) {
            this.state = newState;
            return true;
        }
        return false;
    }
    
    /**
     * Creates a copy of the Survey's Graph.
     * @return copy of the Graph.
     */
    public Graph getGraphCopy(){
        return new Graph(graph);
    }

    /**
     * Returns a description of the survey (product or category description and
     * date)
     *
     * @return survey's description
     */
    @Override
    public String toString() {
        return "Inquerito sobre:\n" + itemList.toString()
                + "\nData:\n" + surveyDate;
    }

    /**
     * Survey's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return itemList.hashCode() + graph.hashCode();
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
        if (!this.itemList.equals(other.itemList)) {
            return false;
        }
        return this.graph.equals(other.graph);
    }
}
