/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain.authz;

import cdioil.domain.Review;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Profile class.
 *
 * @author João
 */
@Entity
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Information of the user's profile
     */
    private String information;
    /**
     * list of the user's reviews
     */
    @OneToMany
    private List<Review> reviews;

    /**
     * list of the user's badges
     */
    //private List<Badge> badges;
    /**
     * list of user sugestions
     */
    //private List<Sugestao> sugestoes;
    /**
     * empty construcotr of Profile class
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
        return reviews.add(r);
    }

    /**
     * Adds a sugestions to the list of seguestions
     *
     * @param s sugestion to be added
     * @return true if the sugestion was added successfully, false if it was not
     * added
     */
    /*public boolean addSugestion(Sugestao s) {
        return sugestoes.add(s);
    }*/
    /**
     * Changes the profile's information
     *
     * @param newInfo profile's new information
     */
    public void changeInformation(String newInfo) {
        information = newInfo;
    }

    /**
     * Profile's hash code
     *
     * @return profile's hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string containing the profile's data
     *
     * @return string containing the profile's data
     */
    @Override
    public String toString() {
        return "org.grupo3.cdioil.isep.feedback_monkey.domain.Perfil[ id=" + id + " ]";
    }

}
