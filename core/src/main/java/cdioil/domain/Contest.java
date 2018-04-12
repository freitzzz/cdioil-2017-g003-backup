package cdioil.domain;

import cdioil.time.TimePeriod;
import cdioil.domain.authz.UsersGroup;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Represents the contest which occurs for a group of app users over a period of
 * time.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "Contest")
public class Contest extends Event implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of Contest with a title, description, a group of
     * users and a time period.
     *
     * @param title contest's title
     * @param description contest's description
     * @param targetAudience contest's target audience
     * @param timePeriod contest's timePeriod
     */
    public Contest(String title, String description, UsersGroup targetAudience,
            TimePeriod timePeriod) {
        super(title, description, targetAudience, timePeriod);
    }

    protected Contest() {
        //For ORM.
    }
}
