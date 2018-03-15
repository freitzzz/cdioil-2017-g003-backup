/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Descreve um Template.
 * @author Ana Guerra (1161191)
 */
@Entity
public class Template implements Serializable {
    
    /**
     * Código de Serialização.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador do Template.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Lista de questoes do Template.
     */
    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    private List<Questao> listaQuestoes;
    /**
     * Lista de inqueritos do Template.
     */
    @OneToMany(mappedBy = "inquerito", cascade = CascadeType.ALL)
    private List<Inquerito> listaInqueritos;
    /**
     * Categoria do Template.
     */
    private Categoria categoria;
    
    /**
     * Constrói uma instância de Template, recebendo por parâmetro os seus atributos.
     *
     * @param questoes Lista de questoes do Template.
     * @param inqueritos  Lista de inqueritos do Template.
     * @param categ Categoria do Template.
     */
    public Template(List<Questao> questoes, List<Inquerito> inqueritos, Categoria categ) {
        this.listaQuestoes = questoes;
        this.listaInqueritos = inqueritos;
        this.categoria = categ;
    }
    /**
     * Constrói uma instância vazia de Template (JPA).
     */
    protected Template() {
    }

    /**
     * Método de acesso à categoria do Template em questão.
     *
     * @return a categoria do Template
     */
    private Categoria getCategoria() {
        return categoria;
    }
    /**
     * Método de acesso à lista de questões do Template em questão.
     *
     * @return a lista de questões do Template
     */
    private List<Questao> getListaQuestoes() {
        return listaQuestoes;
    }
    /**
     * Descreve o Template através da sua Categoria e lista de questoes.
     *
     * @return a descrição textual do Template.
     */
    @Override
    public String toString() {
        return "\nCategoria: " + getCategoria() + "\nLista de Questoes: " + getListaQuestoes();
    }
    
    /**
     * Gera um índice a partir das questões e categorias do Template.
     *
     * @return o valor de hash gerado
     */
    @Override   
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.listaQuestoes);
        hash = 43 * hash + Objects.hashCode(this.categoria);
        return hash;
    }
    /**
     * Compara o Template com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem as mesmas categorias e questões. Caso contrário, retorna false
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
        final Template other = (Template) obj;
        if (!Objects.equals(this.listaQuestoes, other.listaQuestoes)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        return true;
    }
    
}
