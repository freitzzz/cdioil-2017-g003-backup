/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Representa um código EAN (European Article Number)
 * @author Ana Guerra (1161191)
 */
@Embeddable
public class EAN implements Serializable {
    
    /**
     * Código de Serialização.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador do código EAN.
     */
    private int id;

    /**
     * Constrói uma instância de EAN, recebendo por parâmetro o seu ID.
     *
     * @param id Identificador do código EAN
     */
    public EAN(int id) {
        this.id = id;
    }

    /**
     * Constrói uma instância vazia de EAN (JPA).
     */
    protected EAN() {
    }

    /**
     * Método de acesso ao ID do EAN em questão.
     *
     * @return o identificador do EAN
     */
    private int getID() {
        return id;
    }

    /**
     * Descreve o EAN através do seu ID.
     *
     * @return a descrição textual do EAN.
     */
    @Override
    public String toString() {
        return String.format("ID: %d\n", getID());
    }

    /**
     * Gera um índice a partir do ID do EAN.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        return hash;
    }

    /**
     * Compara o EAN com outro objeto.
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

        EAN other = (EAN) obj;

        return this.id == other.id;
    }

}
