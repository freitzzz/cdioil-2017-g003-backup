package cdioil.domain;

import cdioil.application.utils.Graph;
import cdioil.application.utils.Vertex;
import java.util.LinkedList;
import java.util.List;
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
     */
    public ScriptedTemplate(String title) {
        super(title);
        graph = new Graph();
    }

    /**
     * Empty constructor for JPA.
     */
    protected ScriptedTemplate() {
    }

    @Override
    public boolean addQuestion(Question question) {
        return graph.insertVertex(question);
    }

    @Override
    public boolean removeQuestion(Question question) {
        return graph.removeVertex(question);
    }

    @Override
    public Iterable<Question> getQuestions() {

        List<Question> questions = new LinkedList<>();

        Iterable<Vertex> vertices = graph.allVertices();

        for (Vertex v : vertices) {
            questions.add(v.getElement());
        }

        return questions;
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
     * Retrieves the Template's Graph.
     * @return the Template's Graph
     */
    public Graph getGraph(){
        return graph;
    }
    
    /**
     * ScriptedTemplate's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + graph.hashCode();
        hash = 29 * hash + title.hashCode();
        return hash;
    }

    /**
     * Checks if two instances of ScriptedTemplate are equal based on their
     * graph
     *
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

        if (!this.title.equals(other.title)) {
            return false;
        }

        return this.graph.equals(other.graph);
    }
}
