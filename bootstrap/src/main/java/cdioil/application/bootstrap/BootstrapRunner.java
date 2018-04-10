package cdioil.application.bootstrap;

import cdioil.application.bootstrap.authz.UsersBootstrap;
import cdioil.application.bootstrap.authz.WhitelistBootstrap;
import cdioil.application.bootstrap.domain.MarketStructureBootstrap;
import cdioil.application.bootstrap.domain.QuestionLibrariesBootstrap;
import cdioil.domain.Contest;
import cdioil.domain.Survey;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.persistence.impl.EventRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.time.TimePeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

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
        new QuestionLibrariesBootstrap();
    }

    /**
     * Esconde o construtor privado
     */
    private BootstrapRunner() {
    }
}
