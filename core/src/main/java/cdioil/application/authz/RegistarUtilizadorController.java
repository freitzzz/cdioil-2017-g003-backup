package cdioil.application.authz;

import cdioil.persistence.impl.RepositorioUtilizadoresImpl;
import cdioil.domain.authz.*;
import cdioil.persistence.impl.WhitelistRepositoryImpl;

public class RegistarUtilizadorController {

    private RepositorioUtilizadoresImpl repositorioUtilizadoresJPA = new RepositorioUtilizadoresImpl();
    private static final String REGEX_DOMINIO = "[A-Za-z0-9]{1,63}[.][A-Za-z]{2,6}$";
    //private static final String REGEX_SUB_DOMINIO = "[A-Za-z0-9](([A-Za-z0-9]|[.](?![.]))){0,63}";

    public void criarUtilizadorRegistado(String primeiroNome, String apelido, String email, String password) {
        String[] domainSplitted = email.split("@");
        if (domainSplitted.length == 2) {
            String domainPart = domainSplitted[1];
            String realRealDominio=domainPart.replaceAll(REGEX_DOMINIO,"");
            String realDominio="";
            String realSubdominio="";
            if(realRealDominio.isEmpty()){
                realDominio=domainPart;
            }else{
                System.out.println(realRealDominio);
                realDominio=domainPart.replaceAll(realRealDominio,"");
                realSubdominio=realRealDominio.substring(0,realRealDominio.length()-1);
            }
            System.out.println(realDominio);
            System.out.println(realSubdominio);
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
