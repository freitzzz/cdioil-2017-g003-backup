package cdioil.domain;

/**
 * Enum containing all the states a Review can be in.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public enum ReviewState {
    
    PENDING{
        @Override
        public String toString(){
            return "Pendente";
        }
    },
    
    FINISHED{
        @Override
        public String toString(){
            return "Terminada";
        }
    }
    
}
