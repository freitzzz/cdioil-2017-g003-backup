package cdioil.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Representa um código EAN (European Article Number)
 *
 * @author Ana Guerra (1161191)
 */
@Entity(name = "EAN")
@DiscriminatorValue(value = "EAN")
public class EAN extends Codigo<String> {

    /**
     * Construtor protegido para JPA.
     */
    protected EAN() {

    }

    /**
     * Constrói uma nova instância com um dado código.
     *
     * @param codigo código a atribuir
     */
    public EAN(String codigo) {
        this.codigo = codigo;
    }

}
