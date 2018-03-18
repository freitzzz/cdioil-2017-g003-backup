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
     * @param categ Categoria do Template.
     */
    public Template(Categoria categ) {
        this.listaQuestoes = new LinkedList<>();
        this.listaInqueritos = new LinkedList<>();
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
    public boolean adicionarQuestao(Questao questao) {
        if (questao == null || isQuestaoValida(questao)) {
            return false;
        }
        return listaQuestoes.add(questao);
    }
    /**
     * Adiciona um inquerito à lista de inqueritos.
     *
     * @param inquerito INquerito a adicionar
     * @return true, se for adicionado com sucesso. Caso contrário, retorna false
     */
    public boolean adicionarInquerito(Inquerito inquerito) {
        if (inquerito == null || isInqueritoValido(inquerito)) {
            return false;
        }
        return listaInqueritos.add(inquerito);
    }

    /**
     * Remove uma questão da lista de questões.
     *
     * @param questao Questão a remover
     * @return true, se for removida com sucesso. Caso contrário, retorna false
     */
    public boolean removerQuestao(Questao questao) {
        if (questao == null || !isQuestaoValida(questao)) {
            return false;
        }
        return listaQuestoes.remove(questao);
    }
    /**
     * Remove um Inquerito da lista de inqueritos.
     *
     * @param inquerito Questão a remover
     * @return true, se for removido com sucesso. Caso contrário, retorna false
     */
    public boolean removerInquerito(Inquerito inquerito) {
        if (inquerito == null || !isInqueritoValido(inquerito)) {
            return false;
        }
        return listaInqueritos.remove(inquerito);
    }

    /**
     * Verifica se uma questão já existe na lista de questões.
     *
     * @param questao Questão a verificar
     * @return true, se já existir na lista. Caso contrário, retorna false
     */
    public boolean isQuestaoValida(Questao questao) {
        return listaQuestoes.contains(questao);
    }
    /**
     * Verifica se um Inquerito já existe na lista de inqueritos.
     *
     * @param inquerito Inquerito a verificar
     * @return true, se já existir na lista. Caso contrário, retorna false
     */
    public boolean isInqueritoValido(Inquerito inquerito) {
        return listaInqueritos.contains(inquerito);
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
