/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.bootstrap.authz;

import cdioil.application.utils.EmailSenders;
import cdioil.application.utils.OperatorsEncryption;
import cdioil.persistence.impl.EmailSendersRepositoryImpl;

/**
 * Bootstrap for the EmailSenders accounts
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSendersBootstrap {
    /**
     * Builds EmailSendersBootstrap
     */
    public EmailSendersBootstrap(){initializeEmailSenders();}
    /**
     * Initializes Email Senders
     */
    private void initializeEmailSenders(){
        EmailSendersRepositoryImpl sendersRepo=new EmailSendersRepositoryImpl();
        sendersRepo.add(new EmailSenders("feedbackmonkey@outlook.com",OperatorsEncryption.decrypt("15#10#81920#70656#84992#84992#80896#66560#77824#97280#79872#66560#80896#97280#81920#66560#83968#86016#74752#77824#73728#70656#78848#97280#70656#84992#86016#66560#97280#81920#66560#84992#84992#89088#80896#83968#69632#97280#68608#80896#78848#97280#79872#74752#79872#72704#87040#70656#78848")));
        sendersRepo.add(new EmailSenders("feedbackmonkeycostumer@outlook.com",OperatorsEncryption.decrypt("892198234#116966817#116966897#116966886#116966900#116966900#116966896#116966882#116966893#116966912#116966895#116966882#116966896#116966912#116966897#116966882#116966899#116966901#116966890#116966893#116966889#116966886#116966894#116966912#116966886#116966900#116966901#116966882#116966912#116966897#116966882#116966900#116966900#116966904#116966896#116966899#116966885#116966912#116966884#116966896#116966894#116966912#116966895#116966890#116966895#116966888#116966902#116966886#116966894")));
        sendersRepo.add(new EmailSenders("feedbackmonkeycostumerservice@outlook.com",OperatorsEncryption.decrypt("950#659#52720#45471#54697#54697#52061#42835#50084#62605#51402#42835#52061#62605#52720#42835#54038#55356#48107#50084#47448#45471#50743#62605#45471#54697#55356#42835#62605#52720#42835#54697#54697#57333#52061#54038#44812#62605#44153#52061#50743#62605#51402#48107#51402#46789#56015#45471#50743")));
    }
}
