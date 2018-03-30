package cdioil.domain;

import cdioil.time.TimePeriod;
import cdioil.domain.authz.UsersGroup;
import java.io.Serializable;
import javax.persistence.Entity;

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
     * =========================================================================
     * TODO add graph after being implemented.
     * =========================================================================
     */
    /**
     * Builds a Questionnaire with a title, description, target audience and
     * time period.
     *
     * @param title questionnaire's title
     * @param description questionnaire's description
     * @param targetAudience questionnaire's target audience
     * @param timePeriod questionnaire's time period
     */
    public Questionnaire(String title, String description, UsersGroup targetAudience,
            TimePeriod timePeriod) {
        super(title, description, targetAudience, timePeriod);
    }

    protected Questionnaire() {
        //For ORM.
    }
}
