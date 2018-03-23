package cdioil.application.authz;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.impl.WhitelistRepositoryImpl;

/**
 * User Story 103's controller.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistController {

    /**
     * Whitelist repository.
     */
    WhitelistRepositoryImpl repo;

    /**
     * Instantiates the controller.
     */
    public AddWhitelistController() {
        repo = new WhitelistRepositoryImpl();
    }

    /**
     * Adds a new e-mail domain to the Whitelist
     *
     * @param domain e-mail domain
     */
    public void addAuthorizedDomain(String domain) {

        //TODO atualizar caso de uso
        Whitelist whitelist = new Whitelist(domain);

        repo.add(whitelist);
    }

}
