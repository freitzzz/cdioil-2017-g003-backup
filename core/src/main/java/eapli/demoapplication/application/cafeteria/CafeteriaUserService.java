/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.application.cafeteria;

import eapli.demoapplication.domain.cafeteria.CafeteriaUser;
import eapli.demoapplication.domain.cafeteria.MecanographicNumber;
import eapli.demoapplication.persistence.CafeteriaUserRepository;
import eapli.demoapplication.persistence.PersistenceContext;
import eapli.demoapplication.domain.authz.Username;

/**
 *
 * @author mcn
 */
public class CafeteriaUserService {

    private final CafeteriaUserRepository repo = PersistenceContext.repositories().cafeteriaUsers();

    public CafeteriaUser findCafeteriaUserByMecNumber(String mecNumber) {
        return this.repo.findByMecanographicNumber(new MecanographicNumber(mecNumber));
    }

    public CafeteriaUser findCafeteriaUserByUsername(Username user) {
        return this.repo.findByUsername(user);
    }
}
