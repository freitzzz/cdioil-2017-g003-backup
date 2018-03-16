package cdioil.application.authz;

import cdioil.persistence.impl.RepositorioUtilizadoresImpl;
import cdioil.domain.authz.*;
import cdioil.persistence.impl.WhitelistRepositoryImpl;

public class RegistarUtilizadorController {

    private RepositorioUtilizadoresImpl repositorioUtilizadoresJPA = new RepositorioUtilizadoresImpl();
    private static final String REGEX_DOMINIO = "[.][A-Za-z]{2,6}";
    private static final String REGEX_SUB_DOMINIO = "[A-Za-z0-9](([A-Za-z0-9]|[.](?![.]))){0,63}";

    public void criarUtilizadorRegistado(String primeiroNome, String apelido, String email, String password) {
        String[] domainSplitted = email.split("@");
        if (domainSplitted.length == 2) {
            String domainPart = domainSplitted[1];
            String realDominio = domainPart.replaceAll(REGEX_SUB_DOMINIO, "");
            String realSubdominio = domainPart.replaceFirst(REGEX_DOMINIO, "");
            Whitelist whitelist = new Whitelist(realDominio, realSubdominio);
            if (new WhitelistRepositoryImpl().getEntity(whitelist) != null) {
                SystemUser systemUser = new SystemUser(new Email(email), new Nome(primeiroNome, apelido), new Password(password));
                UserRegistado userRegistado = new UserRegistado(systemUser);
                repositorioUtilizadoresJPA.addUserRegistado(userRegistado);
            }else{
                throw new IllegalArgumentException("Dominio/Subdominio de email nao autorizado");
            }
        }  
    }
}
