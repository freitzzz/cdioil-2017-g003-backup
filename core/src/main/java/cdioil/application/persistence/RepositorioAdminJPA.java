package cdioil.application.persistence;

import cdioil.domain.authz.Admin;

public class RepositorioAdminJPA extends RepositorioBaseJPA<Admin, Long> implements RepositorioAdmin {

    @Override
    protected String nomeUnidadePersistencia() {
        //TODO String global
        return null;
    }

    @Override
    public Admin addAdmin(Admin admin) {
        return add(admin);
    }


}
