package cdioil.domain;

import cdioil.application.utils.Graph;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Class that represents a scripted template (a template that has a question
 * graph defined and isn't just a group of questions with no flow)
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "ScriptedTemplate")
public class ScriptedTemplate extends Template {

    /**
     * Question Graph that defines the flow of the template's questions.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Graph graph;

    /**
     * Builds an instance of ScriptedTemplate receiving a title and a question
     * group
     *
     * @param title title of the scripted template
     * @param questionGroup question group of the scripted template
     */
    public ScriptedTemplate(String title, QuestionGroup questionGroup) {
        super(title, questionGroup);
        graph = new Graph();
    }

    /**
     * Empty constructor for JPA.
     */
    protected ScriptedTemplate() {
    }

    /**
     * Adds a question to the list of questions and to the graph
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
     * Remove a question from the graph and the template's question group
     *
     * @param question question to be removed
     * @return true if the question was removed, false if otherwise
     */
    public boolean removeQuestion(Question question) {
        if (question == null || !isValidQuestion(question)) {
            return false;
        }
        return graph.removeVertex(question) && getQuestionGroup().removeQuestion(question);
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
     * Checks if a question already exists in the graph and in the question
     * group
     *
     * @param question question to be verified
     * @return true, if it already exists in the graph and in the question group
     * , false if otherwise
     */
    public boolean isValidQuestion(Question question) {
        return graph.vertexExists(question) && getQuestionGroup().containsQuestion(question);
    }

    /**
     * ScriptedTemplate's hash code
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.graph);
        return hash;
    }

    /**
     * Checks if two instances of ScriptedTemplate are equal based on their graph
     * @param obj object to be compared
     * @return true if the objects are equal, false if otherwise
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
        final ScriptedTemplate other = (ScriptedTemplate) obj;
        if (!Objects.equals(this.graph, other.graph)) {
            return false;
        }
        return true;
    }
}
