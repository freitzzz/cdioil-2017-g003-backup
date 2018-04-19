package cdioil.domain;

import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.time.TimePeriod;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Represents a survey that is targeted to a specific group of users.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "TargetedSurvey")
public class TargetedSurvey extends Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Question and Answer graph.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UsersGroup targetAudience;

    /**
     * Builds a Questionnaire with a title, description, target audience and
     * time period.
     *
     * @param itemList survey items that the survey is about
     * @param surveyPeriod survey's time period
     * @param targetAudience group of users that the survey is targeted to
     */
    public TargetedSurvey(List<SurveyItem> itemList, TimePeriod surveyPeriod,
            UsersGroup targetAudience) {
        super(itemList, surveyPeriod);
        if (targetAudience == null) {
            throw new IllegalArgumentException("O grupo de utilizadores não "
                    + "pode ser null");
        }
        this.targetAudience = targetAudience;
    }

    protected TargetedSurvey() {
        //For ORM.
    }

    /**
     * Adds a list of users to the target audience
     *
     * @param users users to add to target audience
     * @return true if users were added successfully, false if not
     */
    public boolean addUsersToGroup(List<RegisteredUser> users) {
        if (users == null) {
            return false;
        }
        for (RegisteredUser u : users) {
            this.targetAudience.addUser(u);
        }
        return true;
    }
}
