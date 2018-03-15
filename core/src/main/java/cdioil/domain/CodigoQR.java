/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

/**
 * Representa um código QR (Quick Responsive Code).
 *
 * @author Rita Gonçalves (1160912)
 */
@Embeddable
public class CodigoQR implements Serializable {

    /**
     * Código de serialização.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador do código QR.
     */
    @Column(name = "ID_CODQR")
    private int id;

    /**
     * Constrói uma instância de CodigoQR, recebendo por parâmetro o seu ID.
     *
     * @param id Identificador do código QR
     */
    public CodigoQR(int id) {
        this.id = id;
    }

    /**
     * Constrói uma instância vazia de CodigoQR (JPA).
     */
    protected CodigoQR() {
    }

    /**
     * Método de acesso ao ID do CodigoQR em questão.
     *
     * @return o identificador do CodigoQR
     */
    private int getID() {
        return id;
    }

    /**
     * Descreve o CodigoQR através do seu ID.
     *
     * @return a descrição textual do CodigoQR.
     */
    @Override
    public String toString() {
        return String.format("ID: %d\n", getID());
    }

    /**
     * Gera um índice a partir do ID do CodigoQR.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }

    /**
     * Compara o CodigoQR com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem o mesmo ID. Caso contrário, retorna false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        CodigoQR other = (CodigoQR) obj;

        return this.id == other.id;
    }
}
