package cdioil.domain;

import cdioil.domain.authz.UsersGroup;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.*;

/**
 * Represents the contest which occurs for a group of app users over a period of time.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Contest extends Event implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Id of the database to persist contest.
     */
    private Long id;

    /**
     * Description of the contest.
     */
    private String description;
    /**
     * Users to whom the contest is intended.
     */
    private UsersGroup users;
    /**
     * Begind date of the contest.
     */
    @Temporal(TemporalType.DATE)
    private Calendar beginDate;
    /**
     * End date of the contest.
     */
    @Temporal(TemporalType.DATE)
    private Calendar endDate;

    /**
     * Construt a instance of the Constest recibe the description, a group of the users,
     * begind date and end date.
     *
     * @param description description of the contest
     * @param users group of users for whom the Constest is
     * @param beginDate begin date of the contest
     * @param endDate end date of the contest
     */
    public Contest(String description, UsersGroup users,
            Calendar beginDate, Calendar endDate) {
        super(users);
        if (description == null) {
            throw new IllegalArgumentException("O concurso tem que ter uma "
                    + "descrição.");
        }

        if (beginDate == null) {
            throw new IllegalArgumentException("O concurso tem que ter uma data"
                    + " de inicio.");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("O concurso tem que ter uma data"
                    + "de fecho.");
        }
        this.description = description;
        this.users = users;
        this.beginDate = beginDate;
        this.endDate = endDate;

    }

    protected Contest() {
        //Para ORM
    }

   /**
     * Generates an unique index for the Constest.
     *
     * @return the hash value
     */
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Compares a Constest with another Object.
     *
     * @param object Object to compare
     * @return true, if the two Objects are equal. Otherwise, returns false
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Contest)) {
            return false;
        }
        Contest other = (Contest) object;
        return this.description.equals(other.description);
    }

    /**
     * Describes a Constest through its description.
     *
     * @return description of the Constest
     */
    public String toString() {
        return description;
    }

    /**
     * Returns teh information of the Constest.
     *
     * @return the information of the constest with a String
     */
    public String info() {
        return toString();
    }
}
