package cdioil.application.persistence;

import cdioil.domain.authz.SystemUser;

public class RepositorioSystemUserJPA extends RepositorioBaseJPA<SystemUser, Long> implements RepositorioSystemUser {

    @Override
    protected String nomeUnidadePersistencia() {
        //TODO String global
        return null;
    }

    @Override
    public SystemUser addSystemUser(SystemUser systemUser) {
        return add(systemUser);
    }
}
