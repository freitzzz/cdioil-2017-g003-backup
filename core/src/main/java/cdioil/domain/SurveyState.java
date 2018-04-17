package cdioil.domain;

/**
 * Enum that contains all the states that a survey can be in.
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public enum SurveyState {

        /**
         * Draft state of a survey.
         */
        DRAFT{
            @Override
            public String toString(){
                return "Draft";
            }
        },
        
        /**
         * Active state of a survey.
         */
        ACTIVE{
            @Override
            public String toString(){
                return "Ativo";
            }
        },
        
        /**
         * Closed state of a survey.
         */
        CLOSED{
            @Override
            public String toString(){
                return "Fechado";
            }
        };   
}
    

