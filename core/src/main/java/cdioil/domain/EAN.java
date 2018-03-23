package cdioil.domain;

import javax.persistence.Entity;

/**
 * Rpresnets the code EAN (European Article Number)
 *
 * @author Ana Guerra (1161191)
 */
@Entity(name = "EAN")
public class EAN extends Code<String> implements ValueObject {

    /**
     * Protected constructor for JPA.
     */
    protected EAN() {

    }
    /**
     * Constructs a new instance with a given code.
     *
     * @param code code to be a attribute
     */
    public EAN(String code) {
        this.code = code;
    }

}
