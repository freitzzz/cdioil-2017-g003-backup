package cdioil.application.persistence;

import cdioil.domain.authz.UserRegistado;

public class RepositorioUtilizadoresJPA extends RepositorioBaseJPA<UserRegistado, Long> implements RepositorioUtilizadores {

    @Override
    protected String nomeUnidadePersistencia() {
        return null;
    }

    @Override
    public UserRegistado addUserRegistado(UserRegistado userRegistado) {
        return super.add(userRegistado);
    }
}
