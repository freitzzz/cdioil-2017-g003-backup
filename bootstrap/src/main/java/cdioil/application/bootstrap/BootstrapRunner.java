package cdioil.application.bootstrap;

import cdioil.application.bootstrap.authz.EmailSendersBootstrap;
import cdioil.application.bootstrap.authz.UsersBootstrap;
import cdioil.application.bootstrap.authz.WhitelistBootstrap;
import cdioil.application.bootstrap.domain.MarketStructureBootstrap;
import cdioil.application.bootstrap.domain.LibrariesBootstrap;
import cdioil.application.bootstrap.domain.SurveyBootstrap;

/**
 * Classe que corre o bootstrap
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class BootstrapRunner {

    /**
     * Corre o bootstrap
     *
     * @param args Argumentos do bootstrap
     */
    public static void main(String[] args) {
        new UsersBootstrap();
        new WhitelistBootstrap();
        new MarketStructureBootstrap();
        new LibrariesBootstrap();
        new SurveyBootstrap();
        new EmailSendersBootstrap();
    }

    /**
     * Esconde o construtor privado
     */
    private BootstrapRunner() {
    }
}
