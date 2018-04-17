package cdioil.domain;

import cdioil.application.utils.Graph;
import cdioil.time.TimePeriod;

import java.io.Serializable;
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
    @Embedded
    private TimePeriod surveyPeriod;

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
     * @param surveyPeriod survey's time period (can be timed or timeless)
     */
    public Survey(List<SurveyItem> itemList, TimePeriod surveyPeriod) {
        if (itemList == null || itemList.isEmpty()) {
            throw new IllegalArgumentException("O inquérito tem que ter pelo menos"
                    + " um produto ou uma categoria");
        }
        if(surveyPeriod == null){
            throw new IllegalArgumentException("O inquérito ter que ter um período "
                    + "de tempo definido");
        }
        this.itemList = itemList;
        this.graph = new Graph();
        this.surveyPeriod = surveyPeriod;
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
     * Sets the next question for a given option.
     *
     * @param origin current question
     * @param destination next question
     * @param option option leading to the next question
     * @param weight statistical value
     * @return true - if option doesn't already lead to another question<p>
     * false - otherwise
     */
    public boolean setNextQuestion(Question origin, Question destination, QuestionOption option, double weight) {
        return graph.insertEdge(origin, destination, option, 0);
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
     *
     * @return copy of the Graph.
     */
    public Graph getGraphCopy() {
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
                + "\nData:\n" + surveyPeriod.toString();
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
