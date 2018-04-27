package cdioil.persistence.impl;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.WhitelistRepository;
import java.util.List;

/**
 *
 * @author António Sousa [1161371]
 */
public class WhitelistRepositoryImpl extends BaseJPARepository<Whitelist, String> implements WhitelistRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    public List<String> allWhitelistInString() {
        return (List<String>) entityManager().createNativeQuery("SELECT * FROM WHITELIST").getResultList();
    }

    /**
     * Retrives the whitelist with the given domain (case insensitive).
     *
     * @param domain email domain
     * @return the whitelist with the given domain
     */
    @Override
    public Whitelist find(String domain) {

        if (domain == null || domain.trim().isEmpty()) {
            return null;
        }
        String newDomain = domain.toLowerCase();
        List<Whitelist> list= entityManager().createQuery("SELECT w FROM Whitelist "
                + "w WHERE w.domain = :p_domain").setParameter("p_domain", newDomain).getResultList();
        if(list.isEmpty())return null;
        return list.get(0);
    }

    /**
     * Adds a new whitelist to the repository.
     *
     * @param whiteList whitelist to be added
     * @return the whitelist passed as parameter
     */
    @Override
    public Whitelist add(Whitelist whiteList) {
        if (find(whiteList.getID()) != null) {
            return whiteList;
        } else {
            return super.add(whiteList);
        }
    }
}
