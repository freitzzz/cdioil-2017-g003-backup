package cdioil.application.bootstrap;

import cdioil.application.bootstrap.authz.EmailSendersBootstrap;
import cdioil.application.bootstrap.authz.UsersBootstrap;
import cdioil.application.bootstrap.authz.WhitelistBootstrap;
import cdioil.application.bootstrap.domain.MarketStructureBootstrap;
import cdioil.application.bootstrap.domain.LibrariesBootstrap;
import cdioil.application.bootstrap.domain.SurveyBootstrap;

/**
 * Class that runs the bootstrap
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class BootstrapRunner {
    /**
     * Hides public constructor.
     */
    private BootstrapRunner() {
    }
    /**
     * Runs the bootstrap
     *
     * @param args argument list of main method
     */
    public static void main(String[] args) {
//        new UsersBootstrap();
//        new WhitelistBootstrap();
        new MarketStructureBootstrap();
        new LibrariesBootstrap();
        new SurveyBootstrap();
//        new EmailSendersBootstrap();
    }
}
