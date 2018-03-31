package cdioil.application.authz;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.util.LinkedList;
import java.util.List;

/**
 * User Story 103's controller.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistController {

    /**
     * Whitelist repository.
     */
    private WhitelistRepositoryImpl repo;

    /**
     * Instantiates the controller.
     */
    public AddWhitelistController() {
        repo = new WhitelistRepositoryImpl();
    }

    /**
     * Retrieves an Iterable Collection of String with domains that have already
     * been whitelisted.
     *
     * @return Iterable Collection with the whitelisted domains
     */
    public Iterable<String> getExistingEntries() {

        List<String> result = new LinkedList<>();

        Iterable<Whitelist> whitelistedDomains = repo.findAll();

        for (Whitelist domain : whitelistedDomains) {
            result.add(domain.toString());
        }

        return result;
    }

    /**
     * Adds a new e-mail domain to the Whitelist
     *
     * @param domain e-mail domain
     */
    public void addAuthorizedDomain(String domain) {

        Whitelist whitelist = new Whitelist(domain);

        repo.add(whitelist);
    }

}
