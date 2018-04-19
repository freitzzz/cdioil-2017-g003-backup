package cdioil.domain;

import cdioil.time.TimePeriod;
import java.util.List;
import javax.persistence.Entity;

/**
 * Represents a global survey (survey that is targeted at all users)
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "GlobalSurvey")
public class GlobalSurvey extends Survey{

    public GlobalSurvey(List<SurveyItem> itemList, TimePeriod surveyPeriod){
        super(itemList,surveyPeriod);
    }
    
    /**
     * Empty constructor for JPA.
     */
    protected GlobalSurvey(){
    }
}
