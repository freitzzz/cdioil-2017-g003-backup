package cdioil.backoffice.application.authz;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * User Story 103's controller.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistController implements Serializable {
    
    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 19L;



    /**
     * Retrieves an Iterable Collection of String with domains that have already
     * been whitelisted.
     *
     * @return Iterable Collection with the whitelisted domains
     */
    public Iterable<String> getExistingEntries() {

        List<String> result = new LinkedList<>();

        Iterable<Whitelist> whitelistedDomains = new WhitelistRepositoryImpl().findAll();

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

        new WhitelistRepositoryImpl().add(whitelist);
    }

    /**
     * Removes an authorized domain from the repository
     * @param domain entry/domain to be removed
     * @return removed domain
     */
    public String removeAuthorizedDomain(String domain) {
        WhitelistRepositoryImpl whiteListRepo=new WhitelistRepositoryImpl();
        Whitelist whitelist = whiteListRepo.find(domain);
        return whiteListRepo.remove(whitelist).toString();
    }

}
