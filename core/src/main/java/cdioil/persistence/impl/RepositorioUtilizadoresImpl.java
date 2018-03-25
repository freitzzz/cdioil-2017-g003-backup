package cdioil.persistence.impl;

import cdioil.persistence.BaseJPARepository;
import cdioil.domain.authz.UserRegistado;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.UserRegistadoRepository;

public class RepositorioUtilizadoresImpl extends BaseJPARepository<UserRegistado,Long> implements UserRegistadoRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    @Override
    public UserRegistado addUserRegistado(UserRegistado userRegistado) {
        return super.add(userRegistado);
    }
    @Override
    public UserRegistado add(UserRegistado user){
        if(exists(user))return user;
        return super.add(user);
    }
    public boolean exists(UserRegistado user){return find(user.getID())!=null;}
}
