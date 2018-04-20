package cdioil.application.bootstrap.authz;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.impl.WhitelistRepositoryImpl;

/**
 * Bootstrap que persiste dominios/subdominios autorizados na base de dados
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class WhitelistBootstrap {
    /**
     * Inicializa o bootstrap da whitelist
     */
    public WhitelistBootstrap(){inicializarDominios();}
    /**
     * Inicializa o bootstrap dos dominios/subdominios autorizados
     */
    private void inicializarDominios(){
        WhitelistRepositoryImpl whiteRepo=new WhitelistRepositoryImpl();
        whiteRepo.add(new Whitelist("gmail.com"));
        whiteRepo.add(new Whitelist("outlook.com"));
        whiteRepo.add(new Whitelist("outlook.pt"));
        whiteRepo.add(new Whitelist("live.com"));
        whiteRepo.add(new Whitelist("hotmail.com"));
        whiteRepo.add(new Whitelist("sonae.pt"));
        whiteRepo.add(new Whitelist("worten.pt"));
        whiteRepo.add(new Whitelist("bit.sonae.pt"));
        whiteRepo.add(new Whitelist("continente.sonae.pt"));
        whiteRepo.add(new Whitelist("mng.continente.sonae.pt"));
        whiteRepo.add(new Whitelist("dsi.continente.sonae.pt"));
        whiteRepo.add(new Whitelist("loja.continente.sonae.pt"));
        whiteRepo.add(new Whitelist("compras.continente.sonae.pt"));
        whiteRepo.add(new Whitelist("isep.ipp.pt"));
        whiteRepo.add(new Whitelist("email.com"));
    }
}
