/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.persistence.impl;

import cdioil.domain.Review;
import cdioil.domain.ReviewState;
import cdioil.domain.authz.Profile;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ProfileRepository;
import java.util.List;

/**
 *
 * @author ricar
 */
public class ProfileRepositoryImpl extends BaseJPARepository<Profile, Long>
        implements ProfileRepository {

    /**
     * Method that returns the persistence unit name that the repository uses
     *
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * TODO Update method with lazy loading Method that returns all the pending
     * reviews of a certain user
     *
     * @param loggedUserProfile profile of a user
     * @return list of all the pending reviews user that a registered user has
     */
    @Override
    public List<Review> findUserPendingReviews(Profile loggedUserProfile) {
        if (loggedUserProfile == null) {
            return null;
        }
        long profileID=(long)entityManager().createQuery("SELECT PR.id FROM Profile PR "
                + "WHERE PR=:PROFILE")
                .setParameter("PROFILE",loggedUserProfile)
                .getSingleResult();
        return (List<Review>)entityManager().createNativeQuery("SELECT * FROM REVIEW RE WHERE RE.REVIEWSTATE=?1 "
                + "AND RE.ID IN (SELECT PR.REVIEWS_ID FROM PROFILE_REVIEW PR WHERE PR.PROFILE_ID= ?2)",Review.class)
                .setParameter(1,ReviewState.PENDING.ordinal())
                .setParameter(2,profileID)
                .getResultList();
    }
}
