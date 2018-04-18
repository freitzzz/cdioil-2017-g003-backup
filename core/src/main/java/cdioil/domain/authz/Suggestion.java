package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Represents a suggestion a user leaves about something (e.g. a new product, a
 * product that was the subject of a survey the user did, etc.)
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Embeddable
public class Suggestion implements Serializable, ValueObject {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * String containing the text of the suggestion.
     */
    private String suggestionText;

    /**
     * Builds a new Suggestion instance with a suggestion text
     *
     * @param suggestionText string containing the suggestion text
     */
    public Suggestion(String suggestionText) {
        if (suggestionText == null || suggestionText.isEmpty()) {
            throw new IllegalArgumentException("A sugestão não pode ser null "
                    + "ou vazia");
        }
        this.suggestionText = suggestionText;
    }

    /**
     * Empty constructor for JPA.
     */
    protected Suggestion() {
    }

    /**
     * Suggestion's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return suggestionText.hashCode();
    }

    /**
     * Checks if two Suggestions are equal.
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Suggestion)) {
            return false;
        }
        final Suggestion other = (Suggestion) obj;
        return this.suggestionText.equals(other.suggestionText);
    }

    /**
     * Returns the suggestion text of a Suggestion
     *
     * @return string with the suggestion text
     */
    @Override
    public String toString() {
        return this.suggestionText;
    }
}
