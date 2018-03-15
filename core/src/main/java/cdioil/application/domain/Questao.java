/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * Representa uma Questao.
 * @author Ana Guerra (1161191)
 */
@Embeddable
public class Questao implements Serializable {

    /**
     * Código de Serialização.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador da Questao.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Descrição da Questao.
     */
    private String descricao;
    /**
     * Valor mínimo de avaliação.
     */
    private double valor_Min;
    /**
     * Valor máximo de avaliação.
     */
    private double valor_Max;
    /**
     * Escala de avaliação.
     */
    private double escala;

    /**
     * Constrói uma instância de Questao, recebendo por parâmetro o seus atributos.
     *
     * @param descricao Descrição da Questao.
     * @param valor_Min Valor mínimo de avaliação.
     * @param valor_Max Valor máximo de avaliação.
     * @param escala Escala de avaliação.
     */
    public Questao(String descricao, double valor_Min, double valor_Max, double escala) {
        this.descricao = descricao;
        this.valor_Min = valor_Min;
        this.valor_Max = valor_Max;
        this.escala = escala;
    }

    /**
     * Constrói uma instância vazia de Questao (JPA).
     */
    protected Questao() {
    }

    /**
     * Método de acesso à descrição da Questao.
     *
     * @return a descrição do Questao
     */
    private String getDescricao() {
        return descricao;
    }

    /**
     * Descreve a Questao através da sua descricao.
     *
     * @return a descrição textual da Questao.
     */
    @Override
    public String toString() {
        return String.format("Descrição da Questão: %s\n", getDescricao());
    }

    /**
     * Gera um índice a partir da descricao da Questao.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.descricao);
        return hash;
    }

    /**
     * Compara a Questao com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem a mesma descricao. Caso contrário, retorna false
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
        final Questao other = (Questao) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        return true;
    }
}
