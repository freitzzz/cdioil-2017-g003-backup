package cdioil.domain;

import cdioil.time.TimePeriod;
import cdioil.domain.authz.UsersGroup;
import cdioil.graph.Graph;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Represents a Questionnaire. A Questionnaire is a sporadic survey, that can be
 * launched at any time by a manager to a certain group of users.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "Questionnaire")
public class Questionnaire extends Event implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Question and Answer graph.
     */
    //@OneToOne
    private Graph<Question, Answer> graph;

    /**
     * Builds a Questionnaire with a title, description, target audience and
     * time period.
     *
     * @param title questionnaire's title
     * @param description questionnaire's description
     * @param targetAudience questionnaire's target audience
     * @param timePeriod questionnaire's time period
     * @param graph questionnaire's question and answer graph
     */
    public Questionnaire(String title, String description, UsersGroup targetAudience,
            TimePeriod timePeriod,Graph<Question,Answer> graph) {
        super(title, description, targetAudience, timePeriod);
        if(graph == null){
            throw new IllegalArgumentException("O questionário tem que ter "
                    + "um conjunto de questões/respostas");
        }
        this.graph = graph;
    }

    protected Questionnaire() {
        //For ORM.
    }
}
