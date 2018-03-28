package cdioil.domain;

import cdioil.time.TimePeriod;
import cdioil.domain.authz.UsersGroup;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Abstract class that represents Events.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Event implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database ID.
     */
    @Id
    private Long id;

    /**
     * Event's title.
     */
    private String title;

    /**
     * Event's description.
     */
    private String description;

    /**
     * Event's target audience.
     */
    private UsersGroup targetAudience;

    /**
     * Time period for which the event is active.
     */
    private TimePeriod timePeriod;

    /**
     * Builds an Event with a title, description, target audience and a time
     * period
     *
     * @param title The event's title
     * @param description The event's description
     * @param targetAudience group of users that the event is targeted to
     * @param timePeriod time period for which the event is active
     */
    public Event(String title, String description, UsersGroup targetAudience,
            TimePeriod timePeriod) {
        if (title == null) {
            throw new IllegalArgumentException("O título do evento não pode ser"
                    + " null");
        }
        if (description == null) {
            throw new IllegalArgumentException("A descrição do evento não pode "
                    + "ser null");
        }
        if (targetAudience == null) {
            throw new IllegalArgumentException("O público alvo não pode ser "
                    + "null");
        }
        if (timePeriod == null) {
            throw new IllegalArgumentException("O período de tempo do evento"
                    + " não pode ser null");
        }
        this.title = title;
        this.description = description;
        this.targetAudience = targetAudience;
        this.timePeriod = timePeriod;
    }

    protected Event() {
        //For ORM
    }

    /**
     * Event's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return title.hashCode() + description.hashCode();
    }

    /**
     * Checks if two instances of Event are equal
     *
     * @param obj object to be compared
     * @return true if they're equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        final Event other = (Event) obj;
        if (!this.title.equals(other.title)) {
            return false;
        }
        return this.description.equals(other.description);
    }

    @Override
    public String toString() {
        return "Evento: " + title + "\nDescricao: " + description + "\n"
                + timePeriod.toString() + "\nPublico Alvo: " + targetAudience.toString();
    }
}
