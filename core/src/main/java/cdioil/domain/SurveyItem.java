package cdioil.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

/**
 * Abstract class that represents an Item of a Survey
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SurveyItem implements Serializable {

    /**
     * Version for JPA.
     */
    @Version
    private Long version;
    
    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Auto-generated database identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Constructor for JPA.
     */
    protected SurveyItem(){}
}
