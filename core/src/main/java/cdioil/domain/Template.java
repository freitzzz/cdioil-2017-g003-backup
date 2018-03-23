/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.io.Serializable;
import java.util.*;
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
    private List<Question> listaQuestoes;
    /**
     * Category do Template.
     */
    private Category categoria;
    
    /**
     * Constrói uma instância de Template, recebendo por parâmetro os seus atributos.
     *
     * @param categ Category do Template.
     */
    public Template(Category categ) {
        this.listaQuestoes = new LinkedList<>();
        this.categoria = categ;
    }
    /**
     * Constrói uma instância vazia de Template (JPA).
     */
    protected Template() {
    }
     /**
     * Adiciona uma questão à lista de questões.
     *
     * @param questao Questão a adicionar
     * @return true, se for adicionada com sucesso. Caso contrário, retorna false
     */
    public boolean adicionarQuestao(Question questao) {
        if (questao == null || isQuestaoValida(questao)) {
            return false;
        }
        return listaQuestoes.add(questao);
    }

    /**
     * Remove uma questão da lista de questões.
     *
     * @param questao Questão a remover
     * @return true, se for removida com sucesso. Caso contrário, retorna false
     */
    public boolean removerQuestao(Question questao) {
        if (questao == null || !isQuestaoValida(questao)) {
            return false;
        }
        return listaQuestoes.remove(questao);
    }
    
    /**
     * Verifica se uma questão já existe na lista de questões.
     *
     * @param questao Questão a verificar
     * @return true, se já existir na lista. Caso contrário, retorna false
     */
    public boolean isQuestaoValida(Question questao) {
        return listaQuestoes.contains(questao);
    }

    /**
     * Método de acesso à categoria do Template em questão.
     *
     * @return a categoria do Template
     */
    private Category getCategoria() {
        return categoria;
    }
    /**
     * Método de acesso à lista de questões do Template em questão.
     *
     * @return a lista de questões do Template
     */
    private List<Question> getListaQuestoes() {
        return listaQuestoes;
    }
    /**
     * Descreve o Template através da sua Category e lista de questoes.
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
