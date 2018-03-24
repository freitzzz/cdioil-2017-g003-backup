package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a Binary Answer (e.g. yes or no answer).
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class BinaryAnswer extends Answer<Boolean> {
    
    /**
     * Builds a BinaryAnswer instance with a boolean value
     * (true == yes, false == no)
     * @param answer answer given by a user
     */
    public BinaryAnswer(Boolean answer){
        if(answer == null){
            throw new IllegalArgumentException("A resposta não pode ser vazia.");
        }
        this.content = answer;
        this.type = QuestionAnswerTypes.BINARY;
    }
    
    /**
     * Empty constructor for JPA.
     */
    protected BinaryAnswer(){
        
    }
    
}
