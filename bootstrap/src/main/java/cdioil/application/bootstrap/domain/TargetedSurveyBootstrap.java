/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.bootstrap.domain;

import cdioil.domain.*;
import cdioil.domain.authz.*;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Targeted Survey Bootstrap.
 * 
 * @author Ana Guerra (1161191)
 */
public class TargetedSurveyBootstrap {
    /**
     * Runs the Targeted Survey bootstrap.
     */
    public TargetedSurveyBootstrap() {
        bootstrapTargetedSurvey();
    }

    /**
     * Bootstrap of the Targeted Survey
     */
    private void bootstrapTargetedSurvey() {
        
        RegisteredUser sad=new RegisteredUserRepositoryImpl().getUsersByDomain("email.com").get(0);
        System.out.println(sad);
        Manager manager=new ManagerRepositoryImpl().findByUserID(33);
        UsersGroup asdd=new UsersGroup(manager);
        asdd.addUser(sad);
        List<SurveyItem> asd=new ArrayList<>();
        asd.add(new MarketStructureRepositoryImpl().findCategoriesByPathPattern("10938").get(0));
        TargetedSurvey survey=new TargetedSurvey(asd,new TimePeriod(LocalDateTime.now(),LocalDateTime.MAX)
                ,asdd);
        survey.changeState(SurveyState.ACTIVE);
        new SurveyRepositoryImpl().add(survey);
            
        System.out.println(new SurveyRepositoryImpl().getUserTergetedSurveys(new RegisteredUserRepositoryImpl().findByUserID(1)));
    
    }
    
}
