package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import javax.persistence.Entity;

/**
 * Rpresnets the productCode EAN (European Article Number)
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
     * Constructs a new instance with a given productCode.
     *
     * @param code productCode to be a attribute
     */
    public EAN(String code) {
        this.productCode = code;
    }

}
