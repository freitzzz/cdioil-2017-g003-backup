package eapli.demoapplication.persistence.inmemory;

import eapli.demoapplication.persistence.DishTypeRepository;
import eapli.demoapplication.persistence.UserRepository;
import eapli.demoapplication.persistence.CafeteriaUserRepository;
import eapli.demoapplication.persistence.OrganicUnitRepository;
import eapli.demoapplication.persistence.RepositoryFactory;
import eapli.demoapplication.persistence.SignupRequestRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    private static UserRepository userRepository = null;
    private static DishTypeRepository dishTypeRepository = null;
    private static OrganicUnitRepository organicUnitRepository = null;
    private static CafeteriaUserRepository cafeteriaUserRepository = null;
    private static SignupRequestRepository signupRequestRepository = null;

    @Override
    public UserRepository users() {
        if (userRepository == null) {
            userRepository = new InMemoryUserRepository();
        }
        return userRepository;
    }

    @Override
    public DishTypeRepository dishTypes() {
        if (dishTypeRepository == null) {
            dishTypeRepository = new InMemoryDishTypeRepository();
        }
        return dishTypeRepository;
    }

    @Override
    public OrganicUnitRepository organicUnits() {
        if (organicUnitRepository == null) {
            organicUnitRepository = new InMemoryOrganicUnitRepository();
        }
        return organicUnitRepository;
    }

    @Override
    public CafeteriaUserRepository cafeteriaUsers() {

        if (cafeteriaUserRepository == null) {
            cafeteriaUserRepository = new InMemoryCafeteriaUserRepository();
        }
        return cafeteriaUserRepository;
    }
    
        @Override
    public SignupRequestRepository signupRequests() {

        if (signupRequestRepository == null) {
            signupRequestRepository = new InMemorySignupRequestRepository();
        }
        return signupRequestRepository;
    }
}
