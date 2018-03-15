package cdioil.application.persistence;

import cdioil.domain.authz.Gestor;

public class RepositorioGestorJPA extends RepositorioBaseJPA<Gestor, Long> implements RepositorioGestor {

    @Override
    protected String nomeUnidadePersistencia() {
        //TODO String global
        return null;
    }

    @Override
    public Gestor addGestor(Gestor gestor) {
        return add(gestor);
    }
}
