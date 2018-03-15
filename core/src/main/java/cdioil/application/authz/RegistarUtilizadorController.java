package cdioil.application.authz;

import cdioil.persistence.impl.RepositorioUtilizadoresJPA;
import cdioil.domain.authz.*;


public class RegistarUtilizadorController {

    private RepositorioUtilizadoresJPA repositorioUtilizadoresJPA = new RepositorioUtilizadoresJPA();


    public void criarUtilizadorRgistado(String primeiroNome, String apelido, String email, String password) {
        SystemUser systemUser = new SystemUser(new Email(email), new Nome(primeiroNome, apelido), new Password(password));
        UserRegistado userRegistado = new UserRegistado(systemUser);

        repositorioUtilizadoresJPA.addUserRegistado(userRegistado);
    }
}
