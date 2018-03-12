package eapli.demoapplication.persistence.jpa;

import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.demoapplication.persistence.UserRepository;
import eapli.demoapplication.persistence.OrganicUnitRepository;
import eapli.demoapplication.persistence.RepositoryFactory;
import eapli.demoapplication.persistence.SignupRequestRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users() {
        return new JpaUserRepository();
    }

    @Override
    public DishTypeRepository dishTypes() {
        return new JpaDishTypeRepository();
    }

    @Override
    public OrganicUnitRepository organicUnits() {
        return new JpaOrganicUnitRepository();
    }

    @Override
    public JpaCafeteriaUserRepository cafeteriaUsers() {
        return new JpaCafeteriaUserRepository();
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository();
    }
}
