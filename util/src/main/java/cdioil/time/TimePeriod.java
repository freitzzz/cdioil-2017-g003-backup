package cdioil.time;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;

/**
 * Represents a time period with a starting date and an ending date.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Embeddable
public class TimePeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Starting date.
     */
    private LocalDateTime startDate;
    /**
     * Ending date.
     */
    private LocalDateTime endDate;

    /**
     * Builds a TimePeriod with a starting date and an ending date.
     *
     * @param startDate starting date of the time period
     * @param endDate ending date of the time period
     */
    public TimePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("A data de início não pode ser "
                    + "null.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("A data de fim não pode ser "
                    + "null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("A data de início não pode ser "
                    + "depois da data de fim.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("A data de fim não pode ser "
                    + "antes da data de início.");
        }
        if (startDate.equals(endDate)) {
            throw new IllegalArgumentException("A data de início não pode ser "
                    + "igual à data de fim.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected TimePeriod() {
        //For ORM.
    }

    /**
     * TimePeriod's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return startDate.hashCode() + endDate.hashCode();
    }

    /**
     * Checks if two TimePeriods are equal by comparing their starting dates and
     * ending dates.
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimePeriod)) {
            return false;
        }
        final TimePeriod other = (TimePeriod) obj;
        if (!this.startDate.equals(other.startDate)) {
            return false;
        }
        return this.endDate.equals(other.endDate);
    }

    /**
     * Returns the starting date and the ending date
     *
     * @return string with the starting date and the ending date
     */
    @Override
    public String toString() {
        return "Data de Inicio: " + startDate.toLocalDate().toString()
                + " " + startDate.toLocalTime().toString() + "\nData de Fim: "
                + endDate.toLocalDate().toString() + " " + endDate.toLocalTime().toString();
    }
}