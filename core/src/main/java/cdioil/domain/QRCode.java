package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a Quick Responsive Code.
 *
 * @author Rita Gonçalves (1160912)
 */
@Entity(name = "QR_CODE")
public class QRCode extends Code<String> {

    /**
     * Empty protected constructor for JPA.
     */
    protected QRCode() {

    }

    /**
     * Creates an instance of QRCode, receiving a String with its code (identifier).
     *
     * @param code Identifer of the QRCode
     */
    public QRCode(String code) {
        this.code = code;
    }
}
