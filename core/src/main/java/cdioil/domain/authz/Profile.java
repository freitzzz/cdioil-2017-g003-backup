package cdioil.domain.authz;

import cdioil.domain.Review;
import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Class that represents a User's profile
 * 
 * TODO Add missing unit tests
 *
 * @author João
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * * @author <a href="1160907@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Profile implements Serializable, AggregateRoot<RegisteredUser> {
    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * RegisteredUser that owns the profile
     */
    @OneToOne
    private RegisteredUser registeredUser;
    /**
     * List of the user's reviews
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Review> reviews;
    /**
     * List of the user's suggestions
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Suggestion> suggestions;

    /**
     * Builds a new Profile of a certain Registered User
     *
     * @param registeredUser RegisteredUser with the registered user that owns
     * this profile
     */
    public Profile(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
        this.reviews = new ArrayList<>();
        this.suggestions = new ArrayList<>();
    }

    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected Profile() {
    }

    /**
     * Access method to the list of reviews
     *
     * @return review list
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Access method to the list of suggestions
     *
     * @return suggestion list
     */
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    /**
     * Adds a review to the list of reviews
     *
     * @param review review to add
     * @return true if the review was added successfully, false if it was not
     * added
     */
    public boolean addReview(Review review) {
        return review != null ? reviews.add(review) : false;
    }

    /**
     * Removes a review from the list of reviews
     *
     * @param review review to remove
     * @return true if the review was removed successfully, false if it was not
     * removed
     */
    public boolean removeReview(Review review) {
        return review != null ? reviews.remove(review) : false;
    }

    /**
     * Adds a suggestion to the list of suggestions
     *
     * @param suggestion suggestion to add
     * @return true if the review was added successfully, false if it was not
     * added
     */
    public boolean addSuggestion(Suggestion suggestion) {
        return suggestion != null ? suggestions.add(suggestion) : false;
    }

    /**
     * Removes a suggestion from the list of suggestions
     *
     * @param suggestion suggestion to remove
     * @return true if the review was removed successfully, false if it was not
     * removed
     */
    public boolean removeSuggestion(Suggestion suggestion) {
        return suggestion != null ? suggestions.remove(suggestion) : false;
    }

    /**
     * Profile's hash code
     *
     * @return profile's hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.registeredUser);
        return hash;
    }

    /**
     * Checks if two instances of Profile are equal
     *
     * @param object object to be compared
     * @return true if instances are equal, false if not
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        return this.getID().equals(other.getID());
    }

    /**
     * Returns a string containing the profile's data
     *
     * @return string containing the profile's data
     */
    @Override
    public String toString() {
        return registeredUser.toString();
    }

    /**
     * Returns the current Profile Identity
     *
     * @return RegisteredUser with the current profile identity
     */
    @Override
    public RegisteredUser getID() {
        return registeredUser;
    }
}
