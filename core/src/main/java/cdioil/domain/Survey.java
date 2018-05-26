package cdioil.domain;

import cdioil.application.utils.Graph;
import cdioil.time.TimePeriod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * Represents a survey
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "surveySeq", initialValue = 1, allocationSize = 1)
public abstract class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "surveySeq")
    /**
     * Database id.
     */
    private long id;

    /**
     * Item associated with the survey.
     */
    //The items on this list can be added to multiple different surveys
    @ManyToMany(cascade = {CascadeType.REFRESH})
    private List<SurveyItem> itemList;

    /**
     * Date of when the survey was done.
     */
    @Embedded
    private TimePeriod surveyPeriod;

    /**
     * Question and Answer graph.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Graph graph;

    /**
     * Survey's state.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @Enumerated(EnumType.STRING)
    private SurveyState state;

    /**
     * Builds an instance of survey with a product and a date.
     *
     * @param itemList list of products or categories the survey is associated to
     * @param surveyPeriod survey's time period (can be timed or timeless)
     */
    public Survey(List<SurveyItem> itemList, TimePeriod surveyPeriod) {
        if (itemList == null || itemList.isEmpty()) {
            throw new IllegalArgumentException("O inquérito tem que ter pelo menos"
                    + " um produto ou uma categoria");
        }
        if (surveyPeriod == null) {
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
     * Access method to the item list of survey.
     *
     * @return the list with all items regarding the survey
     */
    public List<SurveyItem> getItemList() {
        return itemList;
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
    public boolean setNextQuestion(Question origin, Question destination,
            QuestionOption option, double weight) {
        return graph.insertEdge(origin, destination, option, weight);
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
     * Returns the Survey name
     * @return String with the survey name
     */
    public String getName() {
        return "Inquérito: "+id;
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
     * Checks if two Survey instances are the same by comparing their product and question list.
     *
     * @param obj object to be compared
     * @return true if the instances are the same, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Survey other = (Survey) obj;
        if (!this.itemList.equals(other.itemList)) {
            return false;
        }
        return this.graph.equals(other.graph);
    }

    /**
     * Returns a description of the survey (product or category description and date)
     *
     * @return survey's description
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Inquérito:");

        for (SurveyItem surveyItem
                : itemList) {
            stringBuilder.append("\n" + surveyItem.toString());
        }

        stringBuilder.append("\nData: " + surveyPeriod.toString());

        return stringBuilder.toString();
    }

    /**
     * Filters a list of surveys using a product
     *
     * @param surveys list of surveys
     * @param p product to filter by
     * @return list of surveys about the product
     */
    public static List<Survey> getProductSurveys(List<Survey> surveys, Product p) {
        if (surveys == null || surveys.isEmpty() || p == null) {
            return new ArrayList<>();
        }
        List<Survey> filteredSurveys = new ArrayList<>();
        for (Survey survey : surveys) {
            if (survey.itemList.contains(p)) {
                filteredSurveys.add(survey);
            }
        }
        return filteredSurveys;
    }

}
