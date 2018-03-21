package cdioil.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Representa um código QR (Quick Responsive Code).
 *
 * @author Rita Gonçalves (1160912)
 */
@Entity(name = "CODIGOQR")
public class CodigoQR extends Codigo<String> {

    /**
     * Construtor protegido para JPA.
     */
    protected CodigoQR() {

    }

    /**
     * Constrói uma instância com um dado código.
     *
     * @param codigo o código a atribuir
     */
    public CodigoQR(String codigo) {
        this.codigo = codigo;
    }
}
