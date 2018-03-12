/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.persistence.inmemory;

import eapli.demoapplication.domain.cafeteria.OrganicUnit;
import eapli.demoapplication.persistence.OrganicUnitRepository;
import eapli.framework.persistence.repositories.impl.inmemory.InMemoryRepositoryWithLongPK;

/**
 *
 * @author arocha
 */
public class InMemoryOrganicUnitRepository extends InMemoryRepositoryWithLongPK<OrganicUnit>
        implements OrganicUnitRepository {

    @Override
    public OrganicUnit findByAcronym(String acronym) {
        return matchOne(e -> e.id().equals(acronym));
    }
}
