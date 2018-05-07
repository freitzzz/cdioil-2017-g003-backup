/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Profile class.
 *
 * @author João
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Entity
public class Profile implements Serializable, AggregateRoot<RegisteredUser> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * RegisteredUser that owns the profile
     */
    @OneToOne
    private RegisteredUser registeredUser;
    /**
     * list of the user's reviews
     */
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    private List<Review> reviews;
    
    /**
     * Builds a new Profile of a certain Registered User
     *
     * @param registeredUser RegisteredUser with the registered user that owns
     * this profile
     */
    public Profile(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
        this.reviews = new ArrayList<>();
    }

    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected Profile() {
    }

    /**
     * Adds a review to the list of reviews
     *
     * @param r review to add
     * @return true if the review was added successfully, false if it was not
     * added
     */
    public boolean addReview(Review r) {
        return r != null ? reviews.add(r) : false;
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
