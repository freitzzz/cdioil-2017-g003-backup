package eapli.demoapplication.persistence;

import eapli.demoapplication.domain.authz.Username;
import eapli.demoapplication.domain.cafeteria.CafeteriaUser;
import eapli.demoapplication.domain.cafeteria.MecanographicNumber;
import eapli.framework.persistence.repositories.Repository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public interface CafeteriaUserRepository extends Repository<CafeteriaUser, MecanographicNumber> {

    /**
     * returns the demoapplication user (frontoffice) whose username is given
     *
     * @param name the username to search for
     * @return
     */
    CafeteriaUser findByUsername(Username name);

    /**
     * returns the demoapplication user (frontoffice) with the given mecanographic number
     *
     * @param number
     * @return
     */
    CafeteriaUser findByMecanographicNumber(MecanographicNumber number);
}
