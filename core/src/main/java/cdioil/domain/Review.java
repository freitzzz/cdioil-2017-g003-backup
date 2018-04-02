/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Review class.
 *
 * @author João
 */
@Entity
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * user's opinion on the reviewed product
     */
    private String opinion;

    /**
     * product subject to review
     */
    @OneToOne
    @Column(name="SURVEY")
    private Survey survey;
    private Product product;

    /**
     * Empty constructor of class Review
     */
    protected Review() {
    }

    /**
     * Review constructor
     *
     * @param opinion user's opinion
     * @param product product subject to review
     */
    public Review(String opinion, Product product) {
        this.opinion = opinion;
        this.product = product;
    }

    /**
     * Review's hash code.
     *
     * @return review's hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Checks if two instances of Review are equal
     *
     * @param object object to be compared
     * @return true if instances are equal, false if not
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    /**
     * Method that returns all questions and respective answers of the current Review
     * @return Map with all questions and respective answers of the current Review
     */
    public Map<Question,Answer> getReviewQuestionAnswers(){
        return null;
    }
    /**
     * Returns a string containing the review's data
     *
     * @return string containing the review's data
     */
    @Override
    public String toString() {
        return "Avaliação:\nOpinião: " + opinion;
    }

}
